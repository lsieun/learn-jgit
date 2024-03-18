#!/bin/bash

# 第一步，获取当前目录的绝对路径信息
DIR_NAME=$(cd "$(dirname "$0")"; pwd -P)

# 第二步，检测是否存在.git文件夹
GIT_DIR="${DIR_NAME}/.git"
[[ -e ${GIT_DIR} && -d ${GIT_DIR} ]] && echo "Find Git Repository: ${GIT_DIR}" || {
    echo "Not a Git Repository: $(pwd)"
    exit 1
}

# 第三步，通过find .git/objects/ type -f命令找到所有git objects对象
GIT_OBJECTS=$(find "${GIT_DIR}/objects/" -type f)

# 第四步，遍历git objects对象，打印信息
for item in ${GIT_OBJECTS}
do
    # 获取某一个具体object的SHA1值
    object_id="$(basename $(dirname "${item}"))$(basename "${item}")"
    # 打印其SHA1值和类型
    echo "${object_id} $(git cat-file -t ${object_id})"
done

echo "DONE"
