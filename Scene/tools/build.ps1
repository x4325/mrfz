# Build scene.jar  (SFW character mod, deps: basemod + stslib)
$ErrorActionPreference = "Stop"
$JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-8.0.492.9-hotspot"
$STEAM = "G:\SteamLibrary\steamapps"
$PROJ = "$STEAM\workshop\content\646570\Scene"
$MODS = "$STEAM\common\SlayTheSpire\mods"
$WS = "$STEAM\workshop\content\646570"

python "$PROJ\tools\generate_placeholders.py"

$CP = @(
  "$STEAM\common\SlayTheSpire\desktop-1.0.jar",
  "$WS\1605060445\ModTheSpire.jar",
  "$WS\1605833019\BaseMod.jar",
  "$WS\1609158507\StSLib.jar"
) -join ';'

Remove-Item -Recurse -Force "$PROJ\build" -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force -Path "$PROJ\build\classes" | Out-Null

$src = Get-ChildItem "$PROJ\src\main\java" -Recurse -Filter *.java | ForEach-Object { $_.FullName }
$sourcesFile = "$PROJ\build\sources.txt"
$src | Set-Content -Encoding ascii $sourcesFile
& "$JAVA_HOME\bin\javac.exe" -encoding UTF-8 -cp $CP -d "$PROJ\build\classes" "@$sourcesFile"
if ($LASTEXITCODE -ne 0) { Write-Error "javac failed $LASTEXITCODE"; exit 1 }
Copy-Item -Recurse -Force "$PROJ\src\main\resources\*" "$PROJ\build\classes\"

Push-Location "$PROJ\build\classes"
& "$JAVA_HOME\bin\jar.exe" cf "$MODS\scene.jar" .
Pop-Location
Write-Host "Built: $MODS\scene.jar ($((Get-Item "$MODS\scene.jar").Length) bytes)"

