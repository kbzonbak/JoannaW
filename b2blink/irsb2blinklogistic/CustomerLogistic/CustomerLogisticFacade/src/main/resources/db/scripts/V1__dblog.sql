
    create table LOGISTICA.DELIVERYSTATEPD (
        ID  bigserial not null,
        WHEN1 bytea not null,
        who varchar(255) not null,
        ORDER_ID int8,
        ITEM_ID int8,
        DELIVERYSTATETYPEPD_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.DELIVERYSTATETYPEPD (
        ID  bigserial not null,
        NAME varchar(250),
        CODE varchar(50),
        CLOSED boolean,
        SHOWABLE boolean,
        primary key (ID)
    );


    create table logistica.DVR_ORDERDETAILPD (
        ORDER_ID int8 not null,
        ITEM_ID int8 not null,
        BASECOST float8,
        FINALCOST float8,
        VISUALORDER int4,
        UMCBARCODE varchar(250),
        UMCDESCRIPTION varchar(250),
        UMCCODE varchar(250),
        UMBBARCODE varchar(250),
        UMBDESCRIPTION varchar(250),
        UMBCODE varchar(250),
        PRODUCTPACKINGRATE float8,
        RESCHEDULINGDATE bytea,
        TOTALUNITS float8,
        DELIVERYEMAIL float8,
        ORDERSTATETYPEDATE bytea,
        primary key (ORDER_ID, ITEM_ID)
    );

    create table LOGISTICA.ORDERPD (
        ID  bigserial not null,
        NUMBER int8,
        COMMENT varchar(250),
        EMITTED bytea,
        DELIVERYDATE bytea,
        WAREHOUSECODE varchar(50),
        CURRENTSTATETYPE_ID int8 not null,
        VENDOR_ID int8 not null,
        RETAILER_ID int8 not null,
        CLIENT_ID int8,
        RESPONSABLE_ID int8,
        ORDERTYPE_ID int8 not null,
        primary key (ID)
    );
    
lter table LOGISTICA.DELIVERYSTATEPD 
        add constraint FK7s92j3e6ljf7re9vondlvfsrh 
        foreign key (ORDER_ID, ITEM_ID) 
        references logistica.DVR_ORDERDETAILPD;

    alter table LOGISTICA.DELIVERYSTATEPD 
        add constraint FK7bpbdpoqjs9genm13mphdgdvk 
        foreign key (DELIVERYSTATETYPEPD_ID) 
        references LOGISTICA.DELIVERYSTATETYPEPD;

    alter table logistica.DVR_ORDERDETAILPD 
        add constraint FK1ug3o7p9ytj3sw5eq763nget9 
        foreign key (ORDER_ID) 
        references LOGISTICA.ORDERPD;

    alter table logistica.DVR_ORDERDETAILPD 
        add constraint FKobhanw9we84yotopxdphosplp 
        foreign key (ITEM_ID) 
        references LOGISTICA.ITEM;

    alter table LOGISTICA.ORDERPD 
        add constraint FK85bdx6ht65yx31car3l1b9u41 
        foreign key (CURRENTSTATETYPE_ID) 
        references LOGISTICA.ORDERSTATETYPE;

    alter table LOGISTICA.ORDERPD 
        add constraint FKhkav786k25el8rr26m4dwuq34 
        foreign key (VENDOR_ID) 
        references LOGISTICA.VENDOR;

    alter table LOGISTICA.ORDERPD 
        add constraint FKqg8kab44v89mdr0ysea37qtwo 
        foreign key (RETAILER_ID) 
        references LOGISTICA.RETAILER;

    alter table LOGISTICA.ORDERPD 
        add constraint FK35seif8v7esvb2x9ejmy6tnm3 
        foreign key (CLIENT_ID) 
        references LOGISTICA.CLIENT;

    alter table LOGISTICA.ORDERPD 
        add constraint FKog3rs5inmjvpl4x31g0bxyyk 
        foreign key (RESPONSABLE_ID) 
        references LOGISTICA.RESPONSABLE;

    alter table LOGISTICA.ORDERPD 
        add constraint FKqukseny2f4jg0dlx0ln9iwfs1 
        foreign key (ORDERTYPE_ID) 
        references LOGISTICA.ORDERTYPE;
