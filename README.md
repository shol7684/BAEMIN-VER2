# BAEMIN-VER2


![erd](https://user-images.githubusercontent.com/90321316/143839025-c1fd246b-3554-4bf0-a7f8-606f241b0f3a.png)



  CREATE TABLE BM_USER (
    ID NUMBER PRIMARY KEY,
    USERNAME VARCHAR2(100) NOT NULL,
    PASSWORD VARCHAR2(100) NOT NULL,
    EMAIL VARCHAR2(50) ,
    NICKNAME VARCHAR2(50),
    POINT NUMBER DEFAULT 0,
    PHONE VARCHAR2(20) ,
    RATING VARCHAR2(50) DEFAULT 0
    ROLE VARCHAR2(20) DEFAULT 'ROLE_USER'
); 
    -- 유저 번호 자동증가    
    CREATE SEQUENCE USER_ID_SEQ
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 99999999999
       NOCYCLE
       NOCACHE
       NOORDER;


create table bm_store (
    id NUMBER primary key,
    category number NOT NULL,
    store_name varchar2(100) NOT NULL,
    store_address1 varchar2(200) NOT NULL,
    store_address2 varchar2(200) NOT NULL,
    store_address3 varchar2(200),
    store_phone varchar2(20) NOT NULL,
    store_img varchar2(200),
    store_thumb varchar2(200),
    opening_time number DEFAULT 0,
    closing_time number DEFAULT 0,
    min_delevery number DEFAULT 0,
    delevery_time number DEFAULT 0,
    delevery_tip number  DEFAULT 0,
    store_des varchar2(1000) DEFAULT '가게소개가 없습니다'
);

    CREATE SEQUENCE STORE_ID_SEQ
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 99999999999
       NOCYCLE
       NOCACHE
       NOORDER;

create table bm_food (
    id number primary key,
    store_id number NOT NULL,
    food_name varchar2(100) NOT NULL,
    food_price number NOT NULL,
    food_dec varchar2(200), 
    food_img varchar2(200),
    food_thumb varchar2(200)
);

    CREATE SEQUENCE FOOD_ID_SEQ
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 99999999999
       NOCYCLE
       NOCACHE
       NOORDER;
       
ALTER TABLE BM_FOOD
ADD CONSTRAINT FOOD
FOREIGN KEY (STORE_ID)
REFERENCES BM_STORE(ID)
on delete cascade;


-- 음식 추가옵션
create table bm_food_option (
    id number PRIMARY KEY, 
    food_id number not null,
    option_name varchar2(100) not null,
    option_price number not null
);

    CREATE SEQUENCE OPTION_ID_SEQ
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 99999999999
       NOCYCLE
       NOCACHE
       NOORDER;    
    
    
ALTER TABLE BM_FOOD_OPTION
ADD CONSTRAINT FOOD_OPTION
FOREIGN KEY (FOOD_ID)
REFERENCES BM_FOOD(ID)
on delete cascade;



-- 회원 주문정보 테이블
CREATE TABLE BM_ORDER_USER (
    ORDER_NUM NUMBER PRIMARY KEY,
    STORE_ID NUMBER NOT NULL,
    USER_ID NUMBER NOT NULL,
    ORDER_DATE TIMESTAMP DEFAULT SYSDATE,
    PAY_METHOD VARCHAR2(30),
    DELEVERY_STATUS VARCHAR2(50) DEFAULT '주문접수 대기 중',
    PHONE VARCHAR2(20) NOT NULL,
    DELEVERY_ADDRESS1 NUMBER NOT NULL,
    DELEVERY_ADDRESS2 VARCHAR2(200) NOT NULL,
    DELEVERY_ADDRESS3 VARCHAR2(200),
    TOTAL_PRICE NUMBER NOT NULL,
    USED_POINT NUMBER DEFAULT 0,
    REQUEST VARCHAR2(2000),
    IMP_UID VARCHAR2(30) -- 아임포트 결제번호
);


CREATE TABLE BM_ORDER_DETAIL_USER (
    ORDER_NUM NUMBER,
    FOOD_INFO VARCHAR2(2000)
);


ALTER TABLE BM_ORDER_DETAIL_USER
ADD CONSTRAINT ORDER_DETAIL_USER
FOREIGN KEY (ORDER_NUM)
REFERENCES BM_ORDER_USER(ORDER_NUM)
on delete cascade;






-- 비회원 테이블
-- 회원 비회원 union all 하기위해 user_id 컬럼 추가
CREATE TABLE BM_ORDER_NON_USER (
    ORDER_NUM NUMBER PRIMARY KEY,
    STORE_ID NUMBER NOT NULL,
    USER_ID NUMBER NOT NULL,
    ORDER_DATE TIMESTAMP DEFAULT SYSDATE,
    PAY_METHOD VARCHAR2(30),
    DELEVERY_STATUS VARCHAR2(50) DEFAULT '주문접수 대기 중',
    PHONE VARCHAR2(20) NOT NULL,
    DELEVERY_ADDRESS1 NUMBER NOT NULL,
    DELEVERY_ADDRESS2 VARCHAR2(200) NOT NULL,
    DELEVERY_ADDRESS3 VARCHAR2(200),
    TOTAL_PRICE NUMBER NOT NULL,
    USED_POINT NUMBER DEFAULT 0,
    REQUEST VARCHAR2(2000),
    IMP_UID VARCHAR2(30) -- 아임포트 결제번호
);



CREATE TABLE BM_ORDER_DETAIL_NON_USER (
    ORDER_NUM NUMBER,
    FOOD_INFO VARCHAR2(2000)
);

ALTER TABLE BM_ORDER_DETAIL_NON_USER
ADD CONSTRAINT ORDER_DETAIL_NON_USER
FOREIGN KEY (ORDER_NUM)
REFERENCES BM_ORDER_NON_USER(ORDER_NUM)
on delete cascade;




-- 포인트 테이블
CREATE TABLE BM_POINT (
    USER_ID NUMBER,
    USED_DATE TIMESTAMP DEFAULT SYSDATE,
    INFO VARCHAR2(100) NOT NULL,
    POINT NUMBER NOT NULL
);

ALTER TABLE BM_POINT
ADD CONSTRAINT POINT
FOREIGN KEY (USER_ID)
REFERENCES BM_USER(ID)
on delete cascade;





CREATE TABLE BM_REVIEW (
    ORDER_NUM NUMBER PRIMARY KEY,
    STORE_ID NUMBER NOT NULL,
    REVIEW_CONTENT VARCHAR2(3000) NOT NULL,
    BOSS_COMMENT VARCHAR2(3000),
    REGI_DATE TIMESTAMP DEFAULT SYSDATE,
    USER_ID NUMBER NOT NULL,
    SCORE NUMBER NOT NULL,
    REVIEW_IMG VARCHAR2(200) 
);

ALTER TABLE BM_REVIEW
ADD CONSTRAINT REVIEW
FOREIGN KEY (ORDER_NUM)
REFERENCES BM_ORDER_USER(ORDER_NUM)
on delete cascade;




-- 찜하기 테이블
CREATE TABLE BM_LIKES (
    USER_ID NUMBER,
    STORE_ID NUMBER,
    LIKES_DATE TIMESTAMP DEFAULT SYSDATE
);

ALTER TABLE BM_LIKES
ADD CONSTRAINT LIKES_USER_ID
FOREIGN KEY (USER_ID)
REFERENCES BM_USER(ID)
on delete cascade;

ALTER TABLE BM_LIKES
ADD CONSTRAINT LIKES_STORE_ID
FOREIGN KEY (STORE_ID)
REFERENCES BM_STORE(ID)
on delete cascade;

CREATE TABLE BM_POINT (
    USER_ID NUMBER,
    USED_DATE TIMESTAMP DEFAULT SYSDATE,
    INFO VARCHAR2(100) NOT NULL,
    USED_POINT NUMBER NOT NULL
);


CREATE TABLE BM_GIFT_CARD (
    GIFT_CARD_NUM VARCHAR2(50) NOT NULL,
    POINT NUMBER NOT NULL,
    INFO VARCHAR2(100) NOT NULL
);





INSERT INTO BM_STORE (ID, CATEGORY, STORE_NAME, STORE_ADDRESS1, STORE_ADDRESS2, STORE_PHONE) VALUES (STORE_ID_SEQ.NEXTVAL, 100, '도미노피자', '31099', '천안시 서북구 두정동 오성초등학교', '01012341234');



INSERT INTO BM_FOOD (ID, STORE_ID, FOOD_NAME, FOOD_PRICE, FOOD_DEC, FOOD_IMG, FOOD_THUMB) 
VALUES (FOOD_ID_SEQ.NEXTVAL, 1, '불고기피자', '11000', '불고기피자 입니다', '\img\none.gif', '\img\none.gif');

INSERT INTO BM_FOOD (ID, STORE_ID, FOOD_NAME, FOOD_PRICE, FOOD_DEC, FOOD_IMG, FOOD_THUMB) 
VALUES (FOOD_ID_SEQ.NEXTVAL, 1, '포테이토피자', '12000', '포테이토피자 입니다', '\img\none.gif', '\img\none.gif');

INSERT INTO BM_FOOD (ID, STORE_ID, FOOD_NAME, FOOD_PRICE, FOOD_DEC, FOOD_IMG, FOOD_THUMB) 
VALUES (FOOD_ID_SEQ.NEXTVAL, 1, '고구마피자', '4000', '고구마피자 입니다', '\img\none.gif', '\img\none.gif');

INSERT INTO BM_FOOD (ID, STORE_ID, FOOD_NAME, FOOD_PRICE, FOOD_DEC, FOOD_IMG, FOOD_THUMB) 
VALUES (FOOD_ID_SEQ.NEXTVAL, 1, '페퍼로니피자', '21000', '페퍼뢰니피자 입니다', '\img\none.gif', '\img\none.gif');



INSERT INTO BM_FOOD_OPTION VALUES (OPTION_ID_SEQ.NEXTVAL, 1, '치즈크러스트로 변경', 3000);
INSERT INTO BM_FOOD_OPTION VALUES (OPTION_ID_SEQ.NEXTVAL, 1, '파스타 추가', 4000);
INSERT INTO BM_FOOD_OPTION VALUES (OPTION_ID_SEQ.NEXTVAL, 1, '베이컨 토핑 추가', 1000);
INSERT INTO BM_FOOD_OPTION VALUES (OPTION_ID_SEQ.NEXTVAL, 1, '치즈 토핑 추가', 1000);

INSERT INTO BM_GIFT_CARD VALUES ('QKRTNALS' , 50000, '상품권 충전');


COMMIT;

