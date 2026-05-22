@echo off
rem 设置当前控制台为UTF-8编码
chcp 65001 >> nul

git push origin master
git push code master
git push gitee master

echo "结束!"