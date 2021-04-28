
    create table LOGISTICA.BUYER (
        ID  bigserial not null,
        code varchar(255) not null,
        name varchar(255) not null,
        gln varchar(255),
        businessarea varchar(255),
        rut varchar(255) not null,
        dv varchar(255) not null,
        razonsocial varchar(255) not null,
        primary key (ID)
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
        position int4,
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
        primary key (ORDER_ID, SKU_BUYER)
    );

    create table LOGISTICA.DETAILCHARGE (
        ORDER_ID int8 not null,
        SKU_BUYER varchar(255) not null,
        description varchar(255),
        procentaje boolean,
        value float8,
        primary key (ORDER_ID, SKU_BUYER)
    );

    create table LOGISTICA.DETAILDISCOUNT (
        ORDER_ID int8 not null,
        SKU_BUYER varchar(255) not null,
        description varchar(255),
        procentaje boolean,
        value float8,
        primary key (ORDER_ID, SKU_BUYER)
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
        name varchar(255) not null,
        BUYER_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.ORDER (
        ID  bigserial not null,
        created timestamp,
        number int8,
        ticket varchar(255),
        request varchar(255),
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
        BUYER_ID int8 not null,
        VENDOR_ID int8 not null,
        ORDERTYPE_ID int8 not null,
        SOASTATETYPE_ID int8 not null,
        ORDERSTATETYPE_ID int8 not null,
        DELIVERY_PLACE_ID int8 not null,
        SALE_PLACE_ID int8 not null,
        CLIENT_ID int8 not null,
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
        quantity int4,
        primary key (ORDER_ID, SKU_BUYER, LOCAL_ID)
    );

    create table LOGISTICA.SECTION (
        ID  bigserial not null,
        code varchar(255) not null,
        name varchar(255),
        BUYER_ID int8 not null,
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

    alter table LOGISTICA.DETAILCHARGE 
        add constraint FK4d25oo02rcf7njrup3i58jmaj 
        foreign key (ORDER_ID, SKU_BUYER) 
        references LOGISTICA.DETAIL;

    alter table LOGISTICA.DETAILDISCOUNT 
        add constraint FK4uiir9p69jegw2rjtk3fwk3mo 
        foreign key (ORDER_ID, SKU_BUYER) 
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

    alter table LOGISTICA.PENDINGMESSAGE 
        add constraint FK3mx7pp53on5c8tw9dkix56x2y 
        foreign key (PENDINGMESSAGETYPE_ID) 
        references LOGISTICA.PENDINGMESSAGETYPE;

    alter table LOGISTICA.PREDISTRIBUTION 
        add constraint FKdfuffiqoha71gp9obxmd7gm8g 
        foreign key (ORDER_ID, SKU_BUYER) 
        references LOGISTICA.DETAIL;

    alter table LOGISTICA.PREDISTRIBUTION 
        add constraint FK7ykbv0axwkqbx2srx52wbpf55 
        foreign key (LOCAL_ID) 
        references LOGISTICA.LOCATION;

    alter table LOGISTICA.SECTION 
        add constraint FKqfuph4faselvtqqmucdme35ft 
        foreign key (BUYER_ID) 
        references LOGISTICA.BUYER;

    alter table LOGISTICA.SOASTATE 
        add constraint FKf0p73w0ga80llgkjt9dxshl5e 
        foreign key (ORDER_ID) 
        references LOGISTICA.ORDER;

    alter table LOGISTICA.SOASTATE 
        add constraint FKn05b4ogjbanrcutcn353157f7 
        foreign key (SOASTATETYPE_ID) 
        references LOGISTICA.SOASTATETYPE;
