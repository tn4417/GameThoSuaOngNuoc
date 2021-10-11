#include <File.au3>
#include <Array.au3>
#include <Process.au3>
$s = InputBox("IN", "")
Global $arr = _FileListToArray(@ScriptDir)
for $i=1 to $arr[0]
	if ($arr[$i]="rename.au3") then ContinueLoop
	_RunDos("rename "&$arr[$i]&" "&$s&$arr[$i])
Next
