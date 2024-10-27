@echo off
rem 设置当前控制台为UTF-8编码
chcp 65001 >> nul

git rebase origin/main

echo "结束"
pause