# CampusRecruiment
校园招聘网站课程设计

## 一、简介

基于JSP+Servlet的简易校园招聘网站，适合用作大学教学的课程设计。前端基于HTML/CSS，JavaScript，JQuery和BootStrap框架技术，后端基于Servlet技术，持久层基于MySQL数据库技术，实现了校园招聘发布及报名系统，具有用户登录与注册，管理员审核企业凭证，招聘信息管理，学生用户应聘职位，企业用户查阅收到的简历以及模糊查询职位信息等功能。


## 二、功能模块分解

2.1 用户注册与登录
（1）普通用户注册：
输入用户名、密码、确认密码、验证码后，点击注册即可。只有当用户名不存在，密码与确认密码想匹配，验证码输入正确，才可以注册成功；注册过程会将用户的密码进行MD5加密，保证用户数据的安全。
（2）普通用户登录：
输入正确的用户名、密码、验证码，即可登录成功。否则，前端会提示“用户名不存在”、“密码错误”或“验证码不正确”等信息。
（3）企业用户注册：
输入企业名、企业号（将来用于登录的唯一凭证）、密码、确认密码、验证码，并上传用于管理员审核的企业凭证（任意的文件类型：JPG，PNG，DOC，PDF等），点击注册按钮后，前端会提示“注册申请已提交，请等待审核通过”。注册过程会将用户的密码进行MD5加密，保证用户数据的安全。只有当管理员审核通过以后，企业用户才算注册成功。

此功能实现中，后端主要采用DiskFileItemFactory类和ServletFileUpload类来实现文件上传的功能，将前端上传的文件保存在服务器的目录下，并以企业名_企业id_文件名作为文件的名称，确保此文件的文件名是唯一的（因为企业id是唯一的）；然后将企业用户的注册信息封装成BusinessVO对象，写入到verify_company表中，is_verify字段为0，表示未经过审核；等到管理员审核通过了，is_verify字段会被更新为1，即通过了审核，才表示注册成功。


（4）企业用户登录：
输入企业号、密码以及正确的验证码后，且管理员审核通过了的企业用户，才可登录成功，否则前端会提示：“登陆失败，用户不存在！”
2.2 管理员审核企业凭证
（1）访问URL：http://localhost:8989/campus/admin.html：
可进入管理员页面
（2）管理员登录：
输入正确的用户名、密码、验证码，即可登录成功。登录成功后，会进入管理员的管理与审核页面。
（3）审核企业用户注册信息：
管理员会看到所有未经过审核的企业用户注册信息，信息包括公司的名称以及企业凭证超链接；管理员可以点击下载企业凭证的超链接下载企业用户注册时上传的企业凭证，由此来判断该企业用户是否能通过审核。当点击通过审核后，该企业用户注册成功，可以登录；否则，该企业用户注册失败，无法进行登录。

此功能实现中，后端主要是采用文件输出流来实现文件的下载功能的，前端通过ajax请求发送给后端，请求携带了唯一的文件名，后端就可以去服务器的目录下找到相应的文件，并输出给前端。

2.3 企业用户的招聘信息管理：
（1）发布招聘信息：
企业用户填写发布招聘的表单，表单项包括：招聘职位、薪酬、招聘截止日期、工作地点和任职要求。
（2）查看本企业发布的招聘信息：
企业用户点击“我的招聘”导航栏，可以看到本企业发布的所有招聘信息；点击进入某一个具体的招聘信息，可以看到该招聘信息的详情以及收到的应聘简历列表。
（3）查看收到的简历：
收到的简历详情包括有：应聘者的姓名、应聘职位、手机号码、联系邮箱以及简历下载的超链接；点击下载简历的超链接后，就可以下载简历文件，并查看应聘者的简历。
此功能的实现，后端主要是采用文件输出流来实现文件的下载功能的，前端通过ajax请求发送给后端，请求携带了唯一的文件名，后端就可以去服务器的目录下找到相应的文件，并输出给前端。

2.4 普通用户应聘企业发布的职位
（1）查看所有的招聘信息列表：
当用户访问：http://localhost:8989/campus/时，会展示本网站所有的招聘信息。每一个招聘信息包括：公司名称、截止时间、招聘职位和薪资。
（2）模糊搜索相应的招聘信息：
当用户在搜索框中输入相关的关键词时，招聘信息列表会显示包括了关键词的所有招聘信息，让用户快速地定位想要投递的岗位。
（3）查看招聘信息详情：
在招聘信息列表中，点击“查看详情列表”，可以查看更加详尽的招聘信息，包括公司名称、招聘截止时间、招聘职位、任职要求、薪酬、工作地点等信息。
（4）应聘相关职位：
点击“应聘”按钮，如果用户未登录，前端会提示“你还没登录，请先登录”的提示框，并跳转到用户登录页面；用户登录成功后，会自动跳转到刚刚的应聘页面，在提交简历的表单中，填写相应的信息：包括真实姓名、应聘职位、常用手机号码、常用邮箱，并上传文件简历（文件格式为DOC、PDF），点击提交，如果该用户从未投递过该职位，即可投递成功。

此功能实现中，后端主要采用DiskFileItemFactory类和ServletFileUpload类来实现文件上传的功能，将前端上传的文件保存在服务器的目录下，以学生姓名_学生用户名_招聘信息id_简历文件名作为并要保存的文件名，将相关的信息封装成Resume对象，写入到resume表中。

（5）查询本用户投递过的职位
在用户登录后的主界面，点击“我的应聘”导航栏，会跳转到“我的应聘”页面，只要用户有投递过的职位，都会显示出来。

## 三：数据库表设计
 
（1）管理员用户表：admin
字段	类型	主键	说明
username	varchar(255)	√	用户名
password	varchar(255)		密码

（2）普通（学生）用户表：studentUser

字段	类型	主键	说明
username	varchar(255)		用户名
password	varchar(255)		密码
id	int auto_increment	√	主键id

（3）企业表 verify_company
字段	类型	主键	说明
id	int auto_increment	√	主键id
company_name	varchar(255)		企业名称
company_id	varchar(255)		企业号
password	varchar(255)		密码
companyFile	varchar(255)		企业凭证的保存路径
is_verify	int		是否通过审核：0表示否，1表示是

（4）招聘信息表 recruitInformation
字段	类型	主键	说明
id	int auto_increment	√	主键id
companyName	varchar(255)		企业名称
companyId	varchar(255)		企业号
password	varchar(255)		密码
salary	varchar(255)		薪酬
deadLine	date		招聘截止日期
address	varchar(255)		工作地点
applyPosition	varchar(255)		招聘职位
requirement	Text		任职要求

（5）简历表 resume
字段	类型	主键	说明
id	int auto_increment	√	主键id
studentUsername	varchar(255)		学生用户名
studentName	varchar(255)		学生姓名
phoneNum	varchar(255)		手机号
email	varchar(255)		邮箱
deadLine	date		招聘截止日期
applyPosition	varchar(255)		招聘职位
attachmentResume	varchar(255)		投递的简历文件保存的路径
recruitInfoId	int		所应聘职位的id

 
