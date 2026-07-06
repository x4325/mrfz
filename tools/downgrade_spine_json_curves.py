#!/usr/bin/env python3
"""Downgrade Spine 3.8 JSON bezier curves to the <=3.7 array form.

Spine 3.8 writes timeline bezier curves as separate fields:
    {"time":0.5, "angle":10, "curve":0.25, "c2":0, "c3":0.75, "c4":1}
Older runtimes (Slay the Spire bundles spine-libgdx 3.6) only understand:
    {"time":0.5, "angle":10, "curve":[0.25, 0, 0.75, 1]}
and silently fall back to LINEAR interpolation when "curve" is a number,
making animations look stiff.  "stepped" strings are already compatible.

Usage: python3 downgrade_spine_json_curves.py file.json [more.json ...]
Rewrites files in place (minified, UTF-8).
"""
import json
import sys

# spine-libgdx 3.8 SkeletonJson defaults for c2/c3/c4
C2_DEFAULT, C3_DEFAULT, C4_DEFAULT = 0.0, 1.0, 1.0


def convert(node, stats):
    if isinstance(node, dict):
        cur = node.get("curve")
        if isinstance(cur, (int, float)) and not isinstance(cur, bool):
            node["curve"] = [cur,
                             node.pop("c2", C2_DEFAULT),
                             node.pop("c3", C3_DEFAULT),
                             node.pop("c4", C4_DEFAULT)]
            stats[0] += 1
        for v in node.values():
            convert(v, stats)
    elif isinstance(node, list):
        for v in node:
            convert(v, stats)


def check(node, path="$"):
    """Assert no numeric curve / stray c2-c4 fields remain."""
    problems = []
    if isinstance(node, dict):
        cur = node.get("curve")
        if isinstance(cur, (int, float)) and not isinstance(cur, bool):
            problems.append(path + ": numeric curve remains")
        if isinstance(cur, list) and (len(cur) != 4 or not all(
                isinstance(x, (int, float)) for x in cur)):
            problems.append(path + ": malformed curve array")
        for k in ("c2", "c3", "c4"):
            if k in node:
                problems.append(path + ": stray " + k)
        for k, v in node.items():
            problems += check(v, path + "." + str(k))
    elif isinstance(node, list):
        for i, v in enumerate(node):
            problems += check(v, path + "[%d]" % i)
    return problems


def process(path):
    data = json.load(open(path, encoding="utf-8"))
    stats = [0]
    convert(data.get("animations", {}), stats)
    problems = check(data.get("animations", {}))
    if problems:
        raise SystemExit(path + "\n" + "\n".join(problems[:10]))
    with open(path, "w", encoding="utf-8", newline="\n") as f:
        json.dump(data, f, ensure_ascii=False, separators=(",", ":"))
    return stats[0]


if __name__ == "__main__":
    for p in sys.argv[1:]:
        n = process(p)
        print("%s: converted %d curves" % (p, n))
