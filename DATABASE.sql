CREATE user bank identified by bank123;

GRANT bank ALL PRIVILEGES;

CONNECT bank/bank123;

CREATE TABLE manager
(m_num NUMBER(6),
m_last VARCHAR2(20),
m_first VARCHAR2(20),
m_pass VARCHAR2(30),
m_id VARCHAR2(20),
CONSTRAINT manager_c_num_pk PRIMARY KEY (m_num));

INSERT INTO manager VALUES
(1, 'Myers', 'Mark', 'man123', 'manager1');

CREATE TABLE customer
(c_num NUMBER(6)NOT NULL,
c_sin NUMBER(9),
c_lastn VARCHAR2(20),
c_firstn VARCHAR2(20),
c_pass VARCHAR2(30),
c_userid VARCHAR2(20),
CONSTRAINT customer_c_num_pk PRIMARY KEY (c_num));

CREATE TABLE account
(acc_num NUMBER(6) NOT NULL,
acc_pin NUMBER(9),
acc_avalBal NUMBER(13,3),
acc_type VARCHAR2(20),
acc_openDate DATE,
c_num NUMBER(6),
CONSTRAINT account_acc_num_pk PRIMARY KEY (acc_num),
CONSTRAINT account_c_num_customer_fk FOREIGN KEY (c_num)
REFERENCES customer(c_num));

CREATE TABLE checking
(check_num NUMBER(6) NOT NULL,
ch_TransaLimit NUMBER(4),
ch_AvalBal NUMBER(13,3),
ch_AccType VARCHAR2(20),
ch_ExtraFees NUMBER(6,3),
acc_num NUMBER(6),
CONSTRAINT checking_check_num_pk PRIMARY KEY (check_num),
CONSTRAINT checking_acc_num_account_fk FOREIGN KEY (acc_num)
REFERENCES account(acc_num));

CREATE TABLE saving
(sav_num NUMBER(6) NOT NULL,
sav_annualIR NUMBER(6,3),
sav_annualGains NUMBER(10,3),
sav_avalBal NUMBER(13,3)CONSTRAINT savBalLimit_CK CHECK (sav_avalBal >= 0),
sav_AccType VARCHAR2(20),
sav_ExtraFees NUMBER(6,3),
acc_num NUMBER(6),
CONSTRAINT saving_sav_num_pk PRIMARY KEY (sav_num),
CONSTRAINT saving_acc_num_account_fk FOREIGN KEY (acc_num)
REFERENCES account(acc_num));

CREATE TABLE credit
(cred_num NUMBER(6) NOT NULL,
cred_avalBal NUMBER(13,3),
cred_AccType VARCHAR2(20),
cred_extrafees NUMBER(6,3),
acc_num NUMBER(6),
CONSTRAINT credit_cred_num_pk PRIMARY KEY (cred_num),
CONSTRAINT credit_acc_num_account_fk FOREIGN KEY (acc_num)
REFERENCES account(acc_num));

CREATE TABLE transaction
(transa_num NUMBER(6)NOT NULL,
transa_value NUMBER(13,3),
transa_AccType VARCHAR2(20),
transa_Date DATE,
acc_num NUMBER(6),
CONSTRAINT transaction_transa_num_pk PRIMARY KEY (transa_num),
CONSTRAINT transaction_acc_num_account_fk FOREIGN KEY (acc_num)
REFERENCES account(acc_num));