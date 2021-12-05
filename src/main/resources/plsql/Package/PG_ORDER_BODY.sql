CREATE OR REPLACE NONEDITIONABLE PACKAGE BODY APUSER.PG_ORDER AS

  FUNCTION FN_UPSERT_ORDER(
    I_ORDER_ID APUSER.TB_ORDER.ORDER_ID%TYPE,
    I_HOSTER_ID APUSER.TB_ORDER.HOSTER_ID%TYPE,
    I_ORDER_DATE APUSER.TB_ORDER.ORDER_DATE%TYPE,
    I_END_DATE APUSER.TB_ORDER.END_DATE%TYPE,
    I_ORDER_TYPE APUSER.TB_ORDER.ORDER_TYPE%TYPE,
    I_STORE_ID APUSER.TB_ORDER.STORE_ID%TYPE,
    I_CONTACT_METHOD APUSER.TB_ORDER.CONTACT_METHOD%TYPE,
    I_AMOUNT_LIMIT APUSER.TB_ORDER.AMOUNT_LIMIT%TYPE,
    I_STATUS APUSER.TB_ORDER.STATUS%TYPE
  ) RETURN NUMBER 
  AS
  BEGIN
    MERGE INTO APUSER.TB_ORDER O
    USING DUAL 
    ON(
        O.ORDER_ID = I_ORDER_ID
    )
    WHEN MATCHED THEN
        UPDATE SET
            O.UPDATE_TIME = SYSDATE,
            O.ORDER_DATE = I_ORDER_DATE,
            O.END_DATE = I_END_DATE,
            O.ORDER_TYPE = I_ORDER_TYPE,
            O.STORE_ID = I_STORE_ID,
            O.CONTACT_METHOD = I_CONTACT_METHOD,
            O.AMOUNT_LIMIT = I_AMOUNT_LIMIT,
            O.STATUS = I_STATUS
    WHEN NOT MATCHED THEN
        INSERT(
            O.ORDER_ID,
            O.HOSTER_ID,
            O.ORDER_DATE,
            O.END_DATE,
            O.ORDER_TYPE,
            O.STORE_ID,
            O.CONTACT_METHOD,
            O.AMOUNT_LIMIT,
            O.STATUS
        ) 
        VALUES(
            I_ORDER_ID,
            I_HOSTER_ID,
            I_ORDER_DATE,
            I_END_DATE,
            I_ORDER_TYPE,
            I_STORE_ID,
            I_CONTACT_METHOD,
            I_AMOUNT_LIMIT,
            I_STATUS
        )
    ;
    
    RETURN SQL%ROWCOUNT;

  END FN_UPSERT_ORDER;

  FUNCTION FN_GET_ORDER(
    I_ORDER_ID APUSER.TB_ORDER.ORDER_ID%TYPE
  )RETURN SYS_REFCURSOR 
  AS
    O_RESULT SYS_REFCURSOR;
  BEGIN
    OPEN O_RESULT FOR
    SELECT
        O.ORDER_ID,
        O.HOSTER_ID,
        O.ORDER_DATE,
        O.END_DATE,
        O.ORDER_TYPE,
        O.STORE_ID,
        O.CONTACT_METHOD,
        O.AMOUNT_LIMIT,
        O.STATUS
    FROM
        APUSER.TB_ORDER O
    WHERE
        O.ORDER_ID = I_ORDER_ID
    ;

    RETURN O_RESULT;
    
  END FN_GET_ORDER;

  PROCEDURE SP_QRY_ORDERS(
    I_ORDER_ID APUSER.TB_ORDER.ORDER_ID%TYPE,
    I_HOSTER_ID APUSER.TB_ORDER.HOSTER_ID%TYPE,
    I_ORDER_DATE APUSER.TB_ORDER.ORDER_DATE%TYPE,
    I_END_DATE APUSER.TB_ORDER.END_DATE%TYPE,
    I_ORDER_TYPE APUSER.TB_ORDER.ORDER_TYPE%TYPE,
    I_STORE_ID APUSER.TB_ORDER.STORE_ID%TYPE,
    I_CONTACT_METHOD APUSER.TB_ORDER.CONTACT_METHOD%TYPE,
    I_AMOUNT_LIMIT APUSER.TB_ORDER.AMOUNT_LIMIT%TYPE,
    I_STATUS APUSER.TB_ORDER.STATUS%TYPE,
    I_PAGE_NO NUMBER,
    I_PAGE_SIZE NUMBER,
    O_TOTAL OUT NUMBER,
    O_RESULT OUT SYS_REFCURSOR
  ) 
  AS
  ORDER_LIST TT_ORDER_INFO_LIST;
  BEGIN
    SELECT
        O.ORDER_ID,
        O.HOSTER_ID,
        O.ORDER_DATE,
        O.END_DATE,
        O.ORDER_TYPE,
        O.STORE_ID,
        O.CONTACT_METHOD,
        O.AMOUNT_LIMIT,
        O.STATUS
        BULK COLLECT INTO ORDER_LIST
    FROM
        APUSER.TB_ORDER O
    WHERE
        (
            I_ORDER_ID IS NULL
            OR O.ORDER_ID = I_ORDER_ID
        )
        AND(
            I_HOSTER_ID IS NULL
            OR O.HOSTER_ID = I_HOSTER_ID
        )
        AND(
            I_ORDER_DATE IS NULL
            OR O.ORDER_DATE = I_ORDER_DATE
        )
        AND(
            I_END_DATE IS NULL
            OR O.END_DATE = I_END_DATE
        )
        AND(
            I_ORDER_TYPE IS NULL
            OR O.ORDER_TYPE = I_ORDER_TYPE
        )
        AND(
            I_STORE_ID IS NULL
            OR O.STORE_ID =I_STORE_ID
        )
        AND(
            I_CONTACT_METHOD IS NULL
            OR O.CONTACT_METHOD = I_CONTACT_METHOD
        )
        AND(
            I_AMOUNT_LIMIT IS NULL
            OR O.AMOUNT_LIMIT = I_AMOUNT_LIMIT
        )
        AND(
            I_STATUS IS NULL
            OR O.STATUS = I_STATUS
        )  
    ;
    
    O_TOTAL := ORDER_LIST.COUNT;
    
    OPEN O_RESULT FOR
    SELECT
        A.ORDER_ID,
        A.HOSTER_ID,
        A.ORDER_DATE,
        A.END_DATE,
        A.ORDER_TYPE,
        A.STORE_ID,
        A.CONTACT_METHOD,
        A.AMOUNT_LIMIT,
        A.STATUS
    FROM
        TABLE(ORDER_LIST) A
    OFFSET ((I_PAGE_SIZE * (I_PAGE_NO - 1))) ROWS FETCH NEXT I_PAGE_SIZE ROWS ONLY
    ;
    
  END SP_QRY_ORDERS;

END PG_ORDER;