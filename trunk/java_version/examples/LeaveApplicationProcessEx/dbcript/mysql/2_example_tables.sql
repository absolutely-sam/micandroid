alter table T_BIZ_LeaveApp_TASKINSTANCE drop foreign key FK1DED30B872C79ECD;
drop table if exists T_BIZ_LeaveApp_TASKINSTANCE;
drop table if exists T_Biz_EmailMock;
drop table if exists T_Biz_LeaveApplicationInfo;
drop table if exists T_Biz_LeaveApprovalInfo;
drop table if exists T_FF_RT_TASKINSTANCE;
create table T_BIZ_LeaveApp_TASKINSTANCE (TASKINSTANCE_ID varchar(50) not null, SN varchar(255), APPLICANT varchar(255), leave_days integer, approval_flag bit, from_date varchar(255), to_date varchar(255), primary key (TASKINSTANCE_ID));
create table T_Biz_EmailMock (ID varchar(50) not null, user_Id varchar(50) not null, content text not null, create_Time datetime not null, primary key (ID));
create table T_Biz_LeaveApplicationInfo (ID varchar(50) not null, sn varchar(50) not null unique, leaveReason varchar(50), fromDate varchar(50), toDate varchar(50), leaveDays integer, applicant_Id varchar(50), applicant_Name varchar(50), submitTime varchar(50), approval_Flag bit, approval_Detail varchar(50), approver varchar(50), approval_Time datetime, primary key (ID));
create table T_Biz_LeaveApprovalInfo (ID varchar(50) not null, sn varchar(50) not null, approver varchar(50), approval_Flag bit, detail varchar(100), approval_Time datetime, primary key (ID));
create table T_FF_RT_TASKINSTANCE (ID varchar(50) not null, BIZ_TYPE varchar(250) not null, TASK_ID text not null comment '任务id', ACTIVITY_ID varchar(200) not null comment '环节id', NAME varchar(100) not null comment '名称', DISPLAY_NAME varchar(128) comment '名称', STATE integer not null comment '状态', SUSPENDED bit not null comment '是否挂起', TASK_TYPE varchar(10) comment '任务类型,FormTask,ToolTask,SubflowTask', CREATED_TIME datetime not null comment '创建时间', STARTED_TIME datetime comment '启动时间', EXPIRED_TIME datetime comment '到期时间', END_TIME datetime comment '终止时间', ASSIGNMENT_STRATEGY varchar(10) comment 'WorkItem分配策略,取值ALL,ANY', PROCESSINSTANCE_ID varchar(50) not null comment '流程实例id', PROCESS_ID varchar(100) not null comment '流程id', VERSION integer not null comment '流程版本号', TARGET_ACTIVITY_ID varchar(100) comment '后续activity的id', FROM_ACTIVITY_ID text comment '前驱activity的id', STEP_NUMBER integer not null comment '步数', CAN_BE_WITHDRAWN bit not null comment '是否可以被撤销', primary key (ID));
alter table T_BIZ_LeaveApp_TASKINSTANCE add index FK1DED30B872C79ECD (TASKINSTANCE_ID), add constraint FK1DED30B872C79ECD foreign key (TASKINSTANCE_ID) references T_FF_RT_TASKINSTANCE (ID);
create index idx_taskInst_stepNb on T_FF_RT_TASKINSTANCE (STEP_NUMBER);
