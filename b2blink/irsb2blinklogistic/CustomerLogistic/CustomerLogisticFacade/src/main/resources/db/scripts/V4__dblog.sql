
    create table LOGISTICA.ACTION (
        ID  bigserial not null,
        code varchar(255) not null,
        name varchar(255),
        BUYER_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.BUYER (
        ID  bigserial not null,
        code varchar(255) not null,
        name varchar(255) not null,
        gln varchar(255),
        businessarea varchar(255),
        rut varchar(255) not null,
        dv varchar(255) not null,
        razonsocial varchar(255) not null,
        sitename varchar(255) not null,
        billtopartner varchar(255) not null,
        primary key (ID)
    );

    create table LOGISTICA.BUYERVENDOR (
        BUYER_ID int8 not null,
        VENDOR_ID int8 not null,
        CODE varchar(255) not null,
        primary key (BUYER_ID, VENDOR_ID, CODE)
    );

    create table LOGISTICA.CLIENT (
        ID  bigserial not null,
        name varchar(255),
        identity varchar(255),
        check_digit varchar(255),
        phone varchar(255),
        phone2 varchar(255),
        address varchar(255),
        street_number varchar(255),
        departmet_number varchar(255),
        house_number varchar(255),
        region varchar(255),
        commune varchar(255),
        city varchar(255),
        location varchar(255),
        observation varchar(255),
        primary key (ID)
    );

    create table LOGISTICA.DETAIL (
        ORDER_ID int8 not null,
        SKU_BUYER varchar(255) not null,
        POSITION int4 not null,
        skuvendor varchar(255),
        ean13 varchar(255),
        ean13_buyer varchar(255),
        product_description varchar(255),
        code_umc varchar(255),
        description_umc varchar(255),
        code_umb varchar(255),
        description_umb varchar(255),
        umb_x_umc int4,
        quantity_umc int4,
        list_cost float8,
        final_cost float8,
        list_price float8,
        tolerance varchar(255),
        product_delivery_date timestamp,
        observation varchar(255),
        freight_cost float8,
        freight_weight float8,
        numref1 varchar(255),
        numref2 varchar(255),
        costaftertaxes float8,
        innerpack int8,
        primary key (ORDER_ID, SKU_BUYER, POSITION)
    );

    create table LOGISTICA.DETAILCHARGE (
        ORDER_ID int8 not null,
        SKU_BUYER varchar(255) not null,
        POSITION int4 not null,
        description varchar(255),
        procentaje boolean,
        value float8,
        code varchar(255),
        primary key (ORDER_ID, SKU_BUYER, POSITION)
    );

    create table LOGISTICA.DETAILDISCOUNT (
        ORDER_ID int8 not null,
        SKU_BUYER varchar(255) not null,
        POSITION int4 not null,
        description varchar(255),
        procentaje boolean,
        value float8,
        code varchar(255),
        primary key (ORDER_ID, SKU_BUYER, POSITION)
    );

    create table LOGISTICA.HOMOLOGATION (
        ID  bigserial not null,
        code varchar(255) not null,
        name varchar(255),
        value varchar(255),
        BUYER_ID int8 not null,
        VENDOR_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.LOCATION (
        ID  bigserial not null,
        code varchar(255) not null,
        name varchar(255),
        BUYER_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.ORDER (
        ID  bigserial not null,
        created timestamp,
        number int8,
        ticket varchar(255),
        receiptnumber varchar(255),
        request varchar(255),
        status varchar(255),
        numref1 varchar(255),
        numref2 varchar(255),
        numref3 varchar(255),
        total float8,
        issue_date timestamp,
        effectiv_date timestamp,
        expiration_date timestamp,
        commitment_date timestamp,
        payment_condition varchar(255),
        observation varchar(255),
        responsible varchar(255),
        responsible_email varchar(255),
        valid boolean,
        complete boolean,
        currency varchar(255),
        BUYER_ID int8 not null,
        VENDOR_ID int8 not null,
        ORDERTYPE_ID int8 not null,
        ACTION_ID int8 not null,
        SOASTATETYPE_ID int8 not null,
        ORDERSTATETYPE_ID int8 not null,
        DELIVERY_PLACE_ID int8,
        SALE_PLACE_ID int8,
        CLIENT_ID int8,
        SECTION_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.ORDERCHARGE (
        ID  bigserial not null,
        code varchar(255),
        description varchar(255),
        procentaje boolean,
        value float8,
        ORDER_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.ORDERDISCOUNT (
        ID  bigserial not null,
        code varchar(255),
        description varchar(255),
        procentaje boolean,
        value float8,
        ORDER_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.ORDERSTATE (
        ID  bigserial not null,
        when1 timestamp,
        ORDER_ID int8 not null,
        ORDERSTATETYPE_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.ORDERSTATETYPE (
        ID  bigserial not null,
        code varchar(255) not null,
        name varchar(255) not null,
        valid boolean,
        showable boolean,
        primary key (ID)
    );

    create table LOGISTICA.ORDERTYPE (
        ID  bigserial not null,
        code varchar(255) not null,
        name varchar(255),
        BUYER_ID int8 not null,
        ORDERTYPEB2B_ID int8,
        primary key (ID)
    );

    create table LOGISTICA.ORDERTYPEB2B (
        ID  bigserial not null,
        code varchar(255) not null,
        name varchar(255),
        primary key (ID)
    );

    create table LOGISTICA.PENDINGMESSAGE (
        ID  bigserial not null,
        when1 timestamp not null,
        code varchar(255) not null,
        factory varchar(255) not null,
        queue varchar(255) not null,
        charset varchar(255) not null,
        content varchar not null,
        attempts int4 not null,
        lastattempt timestamp not null,
        status int4 not null,
        PENDINGMESSAGETYPE_ID int8,
        primary key (ID)
    );

    create table LOGISTICA.PENDINGMESSAGETYPE (
        ID  bigserial not null,
        code varchar(255) not null,
        description varchar(255) not null,
        priority int4 not null,
        primary key (ID)
    );

    create table LOGISTICA.PREDISTRIBUTION (
        ORDER_ID int8 not null,
        SKU_BUYER varchar(255) not null,
        LOCAL_ID int8 not null,
        POSITION int4 not null,
        quantity int4,
        shipping_quantity varchar(255),
        received_quantity varchar(255),
        pending_quantity varchar(255),
        primary key (ORDER_ID, SKU_BUYER, LOCAL_ID, POSITION)
    );

    create table LOGISTICA.RECEPTION (
        ID  bigserial not null,
        receptionnumber int8,
        ordernumber int8,
        ordertypename varchar(255),
        complete boolean,
        receptiondate timestamp,
        total float8,
        paymentcondition varchar(255),
        observation varchar(255),
        responsible varchar(255),
        ORDERTYPE_ID int8 not null,
        SOASTATETYPE_ID int8 not null,
        BUYER_ID int8,
        VENDOR_ID int8,
        DELIVERY_PLACE_ID int8,
        primary key (ID)
    );

    create table LOGISTICA.RECEPTIONDETAIL (
        ID  bigserial not null,
        position int4,
        skubuyer varchar(255),
        skuvendor varchar(255),
        ean13 varchar(255),
        productdescription varchar(255),
        codeumc varchar(255),
        descriptionumc varchar(255),
        descriptionumb varchar(255),
        quantityumc int4,
        receivedquantity float8,
        listcost float8,
        finalcost float8,
        RECEPTION_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.RECEPTIONLOCATION (
        RECEPTION_ID int8 not null,
        LOCATION_ID int8 not null,
        primary key (RECEPTION_ID, LOCATION_ID)
    );

    create table LOGISTICA.SECTION (
        ID  bigserial not null,
        code varchar(255) not null,
        name varchar(255),
        BUYER_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.SOARECSTATE (
        ID  bigserial not null,
        WHEN1 timestamp not null,
        COMMENT varchar(255),
        RECEPTION_ID int8 not null,
        SOASTATETYPE_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.SOASTATE (
        ID  bigserial not null,
        WHEN1 timestamp not null,
        ORDER_ID int8 not null,
        SOASTATETYPE_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.SOASTATETYPE (
        ID  bigserial not null,
        CODE varchar(250),
        NAME varchar(250),
        primary key (ID)
    );

    create table LOGISTICA.VENDOR (
        ID  bigserial not null,
        code varchar(255) not null,
        name varchar(255) not null,
        rut varchar(255) not null,
        dv varchar(255) not null,
        gln varchar(255),
        address varchar(255),
        phone varchar(255),
        email varchar(255),
        primary key (ID)
    );

    alter table LOGISTICA.LOCATION 
        add constraint UK_ioat6oy3bnnpji7pqpuucow5o unique (code);

    alter table LOGISTICA.VENDOR 
        add constraint UK_snp4rdb6v505akjnnr5lqopc0 unique (rut);

    alter table LOGISTICA.ACTION 
        add constraint FKca6ym2p0uqcitdxl9fkn2d8kl 
        foreign key (BUYER_ID) 
        references LOGISTICA.BUYER;

    alter table LOGISTICA.DETAILCHARGE 
        add constraint FKlbsf2eqm5325ccxmlacdms3pl 
        foreign key (ORDER_ID, SKU_BUYER, POSITION) 
        references LOGISTICA.DETAIL;

    alter table LOGISTICA.DETAILDISCOUNT 
        add constraint FKpnnougrmccy8n4t9m5nxbm9vv 
        foreign key (ORDER_ID, SKU_BUYER, POSITION) 
        references LOGISTICA.DETAIL;

    alter table LOGISTICA.HOMOLOGATION 
        add constraint FK13hh3kgv2qmw6v6rjd3a1wst7 
        foreign key (BUYER_ID) 
        references LOGISTICA.BUYER;

    alter table LOGISTICA.HOMOLOGATION 
        add constraint FKsxq11iyyfrmaapreu6glileqd 
        foreign key (VENDOR_ID) 
        references LOGISTICA.VENDOR;

    alter table LOGISTICA.LOCATION 
        add constraint FKebvjnmrceenveeuutmhkbb4t1 
        foreign key (BUYER_ID) 
        references LOGISTICA.BUYER;

    alter table LOGISTICA.ORDER 
        add constraint FKpm7chwssm8l0b5iwsnlqu23aw 
        foreign key (BUYER_ID) 
        references LOGISTICA.BUYER;

    alter table LOGISTICA.ORDER 
        add constraint FK3k8w42xdsqn89q0bkoh3r77s1 
        foreign key (VENDOR_ID) 
        references LOGISTICA.VENDOR;

    alter table LOGISTICA.ORDER 
        add constraint FKmyjb6jr01p5x7pucxb8by81oo 
        foreign key (ORDERTYPE_ID) 
        references LOGISTICA.ORDERTYPE;

    alter table LOGISTICA.ORDER 
        add constraint FKf4djuude9xt1bqsns62slx06s 
        foreign key (ACTION_ID) 
        references LOGISTICA.ACTION;

    alter table LOGISTICA.ORDER 
        add constraint FKc42mcwehdacehmtcevkr8dcv8 
        foreign key (SOASTATETYPE_ID) 
        references LOGISTICA.SOASTATETYPE;

    alter table LOGISTICA.ORDER 
        add constraint FKro6ekr06aoed6l0y6wh2upnd8 
        foreign key (ORDERSTATETYPE_ID) 
        references LOGISTICA.ORDERSTATETYPE;

    alter table LOGISTICA.ORDER 
        add constraint FKpedyh7rx04rdrb2mmgse7hjfn 
        foreign key (DELIVERY_PLACE_ID) 
        references LOGISTICA.LOCATION;

    alter table LOGISTICA.ORDER 
        add constraint FKeghxgebwqp8e21fbmdtnfysn7 
        foreign key (SALE_PLACE_ID) 
        references LOGISTICA.LOCATION;

    alter table LOGISTICA.ORDER 
        add constraint FKgfx8euilrluqacf2359sck10n 
        foreign key (CLIENT_ID) 
        references LOGISTICA.CLIENT;

    alter table LOGISTICA.ORDER 
        add constraint FKmb5it5mcyht63qfojh76otbfu 
        foreign key (SECTION_ID) 
        references LOGISTICA.SECTION;

    alter table LOGISTICA.ORDERCHARGE 
        add constraint FKlmx1gv3x6nq7qcmm3gy0k0gle 
        foreign key (ORDER_ID) 
        references LOGISTICA.ORDER;

    alter table LOGISTICA.ORDERDISCOUNT 
        add constraint FKs2wrrj01gjsymkm2ughfl5km0 
        foreign key (ORDER_ID) 
        references LOGISTICA.ORDER;

    alter table LOGISTICA.ORDERSTATE 
        add constraint FKph883b8k0tns7qknttx37rmb7 
        foreign key (ORDER_ID) 
        references LOGISTICA.ORDER;

    alter table LOGISTICA.ORDERSTATE 
        add constraint FK2iu73n4g1dogd21iwds6483is 
        foreign key (ORDERSTATETYPE_ID) 
        references LOGISTICA.ORDERSTATETYPE;

    alter table LOGISTICA.ORDERTYPE 
        add constraint FKmln21u47jpfogkbn61mibm27i 
        foreign key (BUYER_ID) 
        references LOGISTICA.BUYER;

    alter table LOGISTICA.ORDERTYPE 
        add constraint FKdl3wf7mpbxcihtmb8oo3axjhe 
        foreign key (ORDERTYPEB2B_ID) 
        references LOGISTICA.ORDERTYPEB2B;

    alter table LOGISTICA.PENDINGMESSAGE 
        add constraint FK3mx7pp53on5c8tw9dkix56x2y 
        foreign key (PENDINGMESSAGETYPE_ID) 
        references LOGISTICA.PENDINGMESSAGETYPE;

    alter table LOGISTICA.PREDISTRIBUTION 
        add constraint FKdxdhp3y9vri6gnrtogvtmr0j0 
        foreign key (ORDER_ID, SKU_BUYER, POSITION) 
        references LOGISTICA.DETAIL;

    alter table LOGISTICA.PREDISTRIBUTION 
        add constraint FK7ykbv0axwkqbx2srx52wbpf55 
        foreign key (LOCAL_ID) 
        references LOGISTICA.LOCATION;

    alter table LOGISTICA.RECEPTION 
        add constraint FKlci7tucqmjuaov4knww0en2x 
        foreign key (ORDERTYPE_ID) 
        references LOGISTICA.ORDERTYPE;

    alter table LOGISTICA.RECEPTION 
        add constraint FKhecsefikivkxjfrsgerqsjdh9 
        foreign key (SOASTATETYPE_ID) 
        references LOGISTICA.SOASTATETYPE;

    alter table LOGISTICA.RECEPTION 
        add constraint FKj6vuer0sgqdxvjxm3hqhwua0i 
        foreign key (BUYER_ID) 
        references LOGISTICA.BUYER;

    alter table LOGISTICA.RECEPTION 
        add constraint FKc02d6ar79drs46yvtyhm5kbyc 
        foreign key (VENDOR_ID) 
        references LOGISTICA.VENDOR;

    alter table LOGISTICA.RECEPTION 
        add constraint FKp46g5wtkbs41ktc1jba3b41bm 
        foreign key (DELIVERY_PLACE_ID) 
        references LOGISTICA.LOCATION;

    alter table LOGISTICA.RECEPTIONDETAIL 
        add constraint FKo23ya2id0f6ti36h1j5g768oa 
        foreign key (RECEPTION_ID) 
        references LOGISTICA.RECEPTION;

    alter table LOGISTICA.RECEPTIONLOCATION 
        add constraint FKlxckdr8v0v4spaftih0e0qdik 
        foreign key (RECEPTION_ID) 
        references LOGISTICA.RECEPTION;

    alter table LOGISTICA.RECEPTIONLOCATION 
        add constraint FK5pfru9fg5cfp6wxx12wou4p2v 
        foreign key (LOCATION_ID) 
        references LOGISTICA.LOCATION;

    alter table LOGISTICA.SECTION 
        add constraint FKqfuph4faselvtqqmucdme35ft 
        foreign key (BUYER_ID) 
        references LOGISTICA.BUYER;

    alter table LOGISTICA.SOARECSTATE 
        add constraint FKda0cfvnulq7s7syf9xbccgrr1 
        foreign key (RECEPTION_ID) 
        references LOGISTICA.RECEPTION;

    alter table LOGISTICA.SOARECSTATE 
        add constraint FKabl7eps2x3r7562fblfxd2c9g 
        foreign key (SOASTATETYPE_ID) 
        references LOGISTICA.SOASTATETYPE;

    alter table LOGISTICA.SOASTATE 
        add constraint FKf0p73w0ga80llgkjt9dxshl5e 
        foreign key (ORDER_ID) 
        references LOGISTICA.ORDER;

    alter table LOGISTICA.SOASTATE 
        add constraint FKn05b4ogjbanrcutcn353157f7 
        foreign key (SOASTATETYPE_ID) 
        references LOGISTICA.SOASTATETYPE;
