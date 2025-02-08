@echo off
rem 设置当前控制台为UTF-8编码
chcp 65001 >> nul

git add .
git commit -a -m "fix gradle"
git fetch
git rebase origin/master

echo "结束"
pause