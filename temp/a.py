#! /bin/env python
# coding=utf-8
import os
import subprocess as sp

spilt_char = "\\" if os.name == "nt" else "/"
split_chars = "------"
git_path = "/home/test/workspace/5X17/amss/lagvm_p/LINUX/android/vendor/chinatsp/domainServices/AMapService"


def parse_xml(xml_file):
    """
    解析XML文件
    :param xml_file: 文件路径
    :return: 所有的project对象，每个对象包含name， path，revision以及upstream内容
    """
    # 解析XML文件
    contents = []
    current_tree = ElementTree(file=xml_file)
    for item in current_tree.iter(tag='project'):
        # 列表中的每一行数据为
        # {
        # 'name': 'qnx/trunk2',
        # 'path': 'qnx/apps/qnx_ap/AMSS/trunk',
        # 'revision': '9958c9a160313fab87c9c615ebaab21b86043b74',
        # 'upstream': 'chinatsp/qcom/sa8155_new'
        # }
        contents.append(item.attrib)
    return contents


def __get_command(command, commit_range):
    """
    组合commit命令，加上提交代码的范围
    :param command:  原始的command命令
    :param commit_range:  提交代码的范围，
        如0d940e4efb23075f5c36fef4dee9e217fcf2de5d和342b7bc03d87ff41cf5ccdcfc6a6c0b7babf36cc
    :return: 真实执行的命令
    """
    if commit_range and len(commit_range) == 2:
        start, end = commit_range
        return commit_range + "^" + start + " " + end
    return command


def __run_command(command, path):
    """
    执行shell命令
    :param command: 命令
    :return: 读取到的行内容
    """
    pi = sp.Popen(command, shell=True, stdout=sp.PIPE, stderr=sp.PIPE, cwd=path)
    return pi.stdout.readlines()


def check_root_path():
    """
    检查执行是否处于当前路径
    :return:
    """
    current_path = os.getcwd()
    repo_folder = spilt_char.join([current_path, ".repo"])
    return os.path.exists(repo_folder) and os.path.isdir(repo_folder)


def parse_commit(path, commit_range=None):
    """
    获取提交者相关信息
    :param commit_range: 提交代码范围
    :param path: 仓库路径
    :return: 提交者的姓名以及邮件
    """
    authors = []
    command = "git log --pretty=format:%an" + split_chars + "%ae"
    command = __get_command(command, commit_range)
    contents = __run_command(command, path)
    for content in contents:
        content = content.replace("\n", "").decode("utf-8")
        info = content.split(split_chars)
        author = info[0]
        email = info[1]
        authors.append((author, email))
    return authors


def parse_date(path, commit_range=None):
    """
    处理时间信息 %Y-%m-%d %H:%M:%S %A
    :param path: 仓库路径
    :param commit_range: 提交代码范围
    :return: 日期信息
    """
    date_list = []
    command = "git log --date=format:'%Y-%m-%d %H:%M:%S %A'"
    command = __get_command(command, commit_range)
    contents = __run_command(command, path)
    for content in contents:
        content = content.replace("\n", "").decode("utf-8")
        if content.startswith("Date"):
            date_list.append(content)
    return date_list


def parse_description(path, commit_range=None):
    """
    处理相关信息，如head， bugID， module， title root_cause solution description changeid等信息
    :param path: 仓库路径
    :param commit_range: 提交代码范围
    :return:
    """
    command = "git log --date=format:'%Y-%m-%d %H:%M:%S %A'"


data = parse_date(git_path)
print data
