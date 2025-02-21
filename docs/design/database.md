# Database Design

## PERSON

person_id (PK, 主键)
citizen_id (公民身份证号, 唯一)
thai_name (泰文名字)
english_name (英文名字)

## CUSTOMER表（扩展注册信息）

customer_id (PK, 主键)
person_id (FK, 外键关联PERSON表, 唯一)
email (注册邮箱, 唯一)
password_hash (密码哈希)
pin_code (6位数字PIN码)

## TELLER表（银行职员信息）

teller_id (PK, 主键)
employee_id (员工编号, 唯一)
thai_name (泰文名字)
english_name (英文名字)

## ACCOUNT表（账户信息）

account_id (PK, 主键)
account_number (7位唯一账户号码)
owner_person_id (FK, 外键关联PERSON表)
balance (余额)
created_at (创建时间)

## TRANSACTION表（交易记录）

transaction_id (PK, 主键)
account_id (FK, 外键关联ACCOUNT表)
type (交易类型: 存款/转账)
amount (金额)
target_account_id (转账时目标账户, 可为空)
transaction_date (交易时间)
