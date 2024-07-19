#!/bin/sh
#项目的git地址
git_path="https://gitee.com/chargeduck/ikundld"
#本地代码位置代码路径
code_path="/home/code"
#项目名称
projectName="ikundld"
#image
imageTag="dld:latest"
#container
containerName="ikun"


#查询docker容器，停止并删除
echo "关闭${containerName}服务，并删除容器"
OLD="$(docker ps --all --quiet --filter=name="$containerName")"
if [ -n "$OLD" ]; then
  docker stop $OLD && docker rm $OLD
fi

#如果项目文件夹不存在说明没有下载过改项目代码，需要检出该项目
if [ ! -d ${code_path}/${projectName} ];
  then
  echo ${projectName}"不存在,检出该项目"
  cd ${code_path}
  echo "进入目录${code_path}"
  git clone ${git_path}
  git checkout develop
else
  echo ${projectName}"项目存在，更新代码"
  cd ${code_path}/${projectName}
  # git reset --hard # 不需要此步操作
  git pull
fi

#删除项目
echo "删除项目下target"
# 在[]文件夹及子文件夹下查找target文件夹并递归删除
find ${code_path}/${projectName} -name 'target' -type d | xargs rm -rf


echo "进入目录${code_path}/${projectName}"
cd ${code_path}/${projectName}
echo "maven打包部署${projectName}"
mvn clean install -Dmaven.test.skip=true

#如果打包出错直接退出
if [ $? -ne 0 ]; then
      echo "打包异常！结束脚本执行！"
      exit 1
fi

echo "进入目录${code_path}/${projectName}"
cd ${code_path}/${projectName}

#删除原有镜像再 构建镜像
echo "删除${imageTag}镜像！"
docker rmi ${imageTag}
echo "构建${imageTag}镜像！"
docker build -t ${imageTag} .
#如果构建出错直接退出
if [ $? -ne 0 ]; then
      echo "构建异常！结束脚本执行！"
      exit 1
fi


#启动项目
echo "加载指定配置,启动项目${containerName}"
docker run --name ${containerName}  -p 9528:9528 \
  --restart always \
  -v ikun-volume:/home/logs/
  -d ${imageTag}

if [ $? -ne 0 ]; then
      echo "构建异常！结束脚本执行！"
      docker logs  ${containerName}
      exit 1
else
  echo "构建成功"
        docker logs  ${containerName}
fi

