
    create table LOGISTICA.BLOCKINGTYPE (
        ID  bigserial not null,
        CODE varchar(250) not null,
        NAME varchar(250) not null,
        primary key (ID)
    );

    create table LOGISTICA.BULK (
        ID  bigserial not null,
        LPNCODE varchar(250),
        TOTALUNITS float8,
        ACTIVE boolean,
        DVRDELIVERY_ID int8 not null,
        DOCUMENT_ID int8,
        primary key (ID)
    );

    create table LOGISTICA.BULKDETAIL (
        BULK_ID int8 not null,
        DVRDELIVERY_ID int8 not null,
        DVRORDER_ID int8 not null,
        ITEM_ID int8 not null,
        LOCATION_ID int8 not null,
        POSITION int4 not null,
        UNITS float8,
        BATCHNUMBER varchar(255),
        primary key (BULK_ID, DVRDELIVERY_ID, DVRORDER_ID, ITEM_ID, LOCATION_ID, POSITION)
    );

    create table LOGISTICA.CHARGEDISCOUNT (
        ID  bigserial not null,
        CHARGE boolean,
        DESCRIPTION varchar(250),
        VISUALORDER int4,
        ISPERCENTAGE boolean,
        VALUE float8,
        primary key (ID)
    );

    create table LOGISTICA.CHARGEDISCOUNTDVRORDER (
        DVRORDER_ID int8 not null,
        CHARGEDISCOUNT_ID int8 not null,
        primary key (DVRORDER_ID, CHARGEDISCOUNT_ID)
    );

    create table LOGISTICA.CHARGEDISCOUNTDVRORDERDETAIL (
        DVRORDER_ID int8 not null,
        ITEM_ID int8 not null,
        position int4 not null,
        CHARGEDISCOUNT_ID int8 not null,
        primary key (DVRORDER_ID, ITEM_ID, position, CHARGEDISCOUNT_ID)
    );

    create table LOGISTICA.CLIENT (
        ID  bigserial not null,
        DNI varchar(250),
        NAME varchar(250),
        address varchar(250),
        PHONE varchar(250),
        EMAIL varchar(250),
        CITY varchar(250),
        COMMUNE varchar(250),
        COMMENT varchar(250),
        STREETNAME varchar(250),
        STREETNUMBER varchar(250),
        DEPARMENTNUMBER varchar(250),
        primary key (ID)
    );

    create table LOGISTICA.CONTACT (
        ID  bigserial not null,
        NAME varchar(250),
        LASTNAME varchar(250),
        CARGO varchar(250),
        EMAIL varchar(250),
        VENDOR_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.DATING (
        ID int8 not null,
        number int8,
        confirmated boolean,
        comment varchar(255),
        confirmationdate timestamp,
        sentstatus boolean,
        VENDOR_ID int8,
        DVRDELIVERY_ID int8,
        primary key (ID)
    );

    create table LOGISTICA.DATINGREQUEST (
        ID  bigserial not null,
        REQUESTER varchar(250) not null,
        EMAIL varchar(250) not null,
        PHONE varchar(250),
        REQUESTDATE timestamp not null,
        TRUCKS int4 not null,
        PALLETS int4,
        ESTIMATEDVOLUME int4 not null,
        ESTIMATEDTIME int4 not null,
        ESTIMATEDBULKS int4 not null,
        NEEDMODULE varchar(250) not null,
        COMMENT varchar(250),
        WHEN1 timestamp not null,
        DVRDELIVERY_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.DATINGRESOURCE (
        DOCK_ID int8 not null,
        MODULE_ID int8 not null,
        active boolean,
        visualorder int4,
        primary key (DOCK_ID, MODULE_ID)
    );

    create table LOGISTICA.DDCCHARGEDISCOUNT (
        ID  bigserial not null,
        CHARGE boolean not null,
        DESCRIPTION varchar(250) not null,
        VISUALORDER int4 not null,
        ISPERCENTAGE boolean not null,
        VALUE float8 not null,
        primary key (ID)
    );

    create table LOGISTICA.DDCDELIVERY (
        ID  bigserial not null,
        NUMBER int8 not null,
        ORIGINALDATE timestamp not null,
        COMMITTEDDATE timestamp,
        CURRENTSTATETYPEDATE timestamp not null,
        CURRENTSTATETYPEWHO varchar(100) not null,
        CURRENTSTATETYPECOMMENT varchar(250),
        DDCORDER_ID int8 not null,
        CURRENTSTATETYPE_ID int8 not null,
        VENDOR_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.DDCDELIVERYDETAIL (
        DDCDELIVERY_ID int8 not null,
        ITEM_ID int8 not null,
        POSITION int4 not null,
        PENDINGUNITS float8 not null,
        AVAILABLEUNITS float8 not null,
        RECEIVEDUNITS float8 not null,
        primary key (DDCDELIVERY_ID, ITEM_ID, POSITION)
    );

    create table LOGISTICA.DDCDELIVERYSTATE (
        ID  bigserial not null,
        WHEN1 timestamp not null,
        USERNAME varchar(100) not null,
        USERTYPE varchar(50) not null,
        STATEDATE timestamp,
        COMMENT varchar(250),
        DDCDELIVERY_ID int8 not null,
        DDCDELIVERYSTATETYPE_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.DDCDELIVERYSTATETYPE (
        ID  bigserial not null,
        CODE varchar(50) not null,
        NAME varchar(250) not null,
        CLOSED boolean not null,
        SHOWABLE boolean not null,
        SELECTABLE boolean not null,
        CODEWS varchar(50) not null,
        primary key (ID)
    );

    create table LOGISTICA.DDCFILE (
        ID  bigserial not null,
        WHEN1 timestamp not null,
        FILENAME varchar(250),
        FILETYPE varchar(50),
        DDCORDER_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.DDCORDER (
        ID int8 not null,
        ORIGINALDELIVERYDATE timestamp not null,
        CURRENTDELIVERYDATE timestamp not null,
        EXPIRATION timestamp not null,
        NEEDUNITS float8 not null,
        TODELIVERYUNITS float8 not null,
        RECEIVEDUNITS float8 not null,
        PENDINGUNITS float8 not null,
        TOTALNEED float8 not null,
        TOTALTODELIVERY float8 not null,
        TOTALRECEIVED float8 not null,
        TOTALPENDING float8 not null,
        PAYMENT_DAYS varchar(250),
        COMMENT varchar(250),
        CURRENTSTATETYPEDATE timestamp not null,
        CURRENTSTATETYPEWHO varchar(100) not null,
        CURRENTSTATETYPECOMMENT varchar(250),
        REFERENCENUMBER varchar(50) not null,
        DISPATCH_GUIDE int8,
        RESCHEDULINGCOUNTER int4,
        CURRENTSTATETYPE_ID int8 not null,
        SALELOCATION_ID int8,
        CURRENTDDCDELIVERY_ID int8,
        primary key (ID)
    );

    create table LOGISTICA.DDCORDERCHARGEDISCOUNT (
        DDCORDER_ID int8 not null,
        DDCCHARGEDISCOUNT_ID int8 not null,
        primary key (DDCORDER_ID, DDCCHARGEDISCOUNT_ID)
    );

    create table LOGISTICA.DDCORDERCHARGEDISCOUNTDETAIL (
        DDCORDER_ID int8 not null,
        ITEM_ID int8 not null,
        position int4 not null,
        DDCCHARGEDISCOUNT_ID int8 not null,
        primary key (DDCORDER_ID, ITEM_ID, position, DDCCHARGEDISCOUNT_ID)
    );

    create table LOGISTICA.DDCORDERDETAIL (
        DDCORDER_ID int8 not null,
        ITEM_ID int8 not null,
        POSITION int4 not null,
        BASECOST float8,
        FINALCOST float8 not null,
        NORMALPRICE float8,
        OFFERPRICE float8,
        PACKINGCODE varchar(50),
        PACKINGESCRIPTION varchar(250) not null,
        BASEUNIT varchar(255) not null,
        PRODUCTPACKINGRATE float8 not null,
        EAN varchar(50) not null,
        BARCODE2 varchar(50),
        COMMENT varchar(250),
        TOLERANCE float8,
        ITEMDELIVERYDATE timestamp,
        NEEDUNITS float8 not null,
        TODELIVERYUNITS float8 not null,
        RECEIVEDUNITS float8 not null,
        PENDINGUNITS float8 not null,
        TOTALNEED float8 not null,
        TOTALTODELIVERY float8 not null,
        TOTALRECEIVED float8 not null,
        TOTALPENDING float8 not null,
        primary key (DDCORDER_ID, ITEM_ID, POSITION)
    );

    create table LOGISTICA.DDCORDERSTATE (
        ID  bigserial not null,
        WHEN1 timestamp not null,
        WHO varchar(100),
        COMMENT varchar(250),
        DDCORDER_ID int8 not null,
        DDCORDERSTATETYPE_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.DDCORDERSTATETYPE (
        ID  bigserial not null,
        CODE varchar(50) not null,
        NAME varchar(250) not null,
        VALID boolean not null,
        SHOWABLE boolean not null,
        VENDORSELECTABLE boolean not null,
        primary key (ID)
    );

    create table LOGISTICA.DOCK (
        ID  bigserial not null,
        code varchar(255),
        visualorder int4,
        active boolean,
        LOCATION_ID int8,
        primary key (ID)
    );

    create table LOGISTICA.DOCUMENT (
        ID  bigserial not null,
        number varchar(255),
        emitteddate timestamp,
        netamount float8,
        iva float8,
        totalamount float8,
        validated boolean,
        closed boolean,
        receptiondate timestamp,
        when1 timestamp,
        receptionnumber int8,
        asnnumber varchar(255),
        comment varchar(255),
        status boolean not null,
        DOCUMENTTYPE_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.DOCUMENTSTATE (
        ID  bigserial not null,
        WHEN1 timestamp not null,
        status boolean not null,
        documentresponsemessage varchar(255),
        documentresponsecode varchar(255),
        user1 varchar(255),
        usertype varchar(255),
        DOCUMENT_ID int8,
        primary key (ID)
    );

    create table LOGISTICA.DOCUMENTTYPE (
        ID  bigserial not null,
        code varchar(255),
        name varchar(255),
        type varchar(255),
        primary key (ID)
    );

    create table LOGISTICA.DVRDELIVERY (
        ID  bigserial not null,
        number int8 not null,
        created timestamp not null,
        currentstatetypedate timestamp,
        pluploaddate timestamp,
        CURRENTSTATETYPE_ID int8 not null,
        VENDOR_ID int8 not null,
        DELIVERYLOCATION_ID int8,
        primary key (ID)
    );

    create table LOGISTICA.DVRDELIVERYSTATE (
        ID  bigserial not null,
        WHEN1 timestamp not null,
        user1 varchar(255),
        usertype varchar(255),
        userwhen timestamp,
        comment varchar(255),
        DVRDELIVERY_ID int8 not null,
        DVRDELIVERYSTATETYPE_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.DVRDELIVERYSTATETYPE (
        ID  bigserial not null,
        code varchar(255),
        description varchar(255),
        valid boolean,
        visible boolean,
        nextaction varchar(255),
        primary key (ID)
    );

    create table LOGISTICA.DVRORDER (
        ID int8 not null,
        DELIVERYDATE timestamp,
        EXPIRATION timestamp,
        PAYMENTTYPE varchar(250),
        COMMENT varchar(250),
        MULTIDELIVERY boolean,
        WAREHOUSECODE varchar(50),
        TOTALUNITS float8,
        TODELIVERYUNITS float8,
        RECEIVEDUNITS float8,
        PENDINGUNITS float8,
        TOTALNEED float8,
        TOTALTODELIVERY float8,
        TOTALRECEIVED float8,
        TOTALPENDING float8,
        CURRENTSTATETYPEDATE timestamp,
        REFERENCENUMBER varchar(50),
        PAYMENT_DAYS varchar(250),
        CLIENT_DELIVERY_DATE timestamp,
        NETAMOUNT float8,
        TAXAMOUNT float8,
        TOTALAMOUNTOC float8,
        RESCHEDULINGDATE timestamp,
        RESCHEDULINGCOUNTER int4,
        CURRENTSTATETYPE_ID int8 not null,
        DELIVERYLOCATION_ID int8,
        SALELOCATION_ID int8,
        primary key (ID)
    );

    create table LOGISTICA.DVRORDERDELIVERY (
        DVRORDER_ID int8 not null,
        DVRDELIVERY_ID int8 not null,
        closed boolean,
        estimatedunits float8,
        receptiondate timestamp,
        primary key (DVRORDER_ID, DVRDELIVERY_ID)
    );

    create table LOGISTICA.DVRORDERDELIVERYDETAIL (
        DVRORDER_ID int8 not null,
        DVRDELIVERY_ID int8 not null,
        ITEM_ID int8 not null,
        LOCATION_ID int8 not null,
        POSITION int4 not null,
        pendingunits float8 not null,
        availableunits float8 not null,
        receivedunits float8 not null,
        primary key (DVRORDER_ID, DVRDELIVERY_ID, ITEM_ID, LOCATION_ID, POSITION)
    );

    create table LOGISTICA.DVRORDERDETAIL (
        DVRORDER_ID int8 not null,
        ITEM_ID int8 not null,
        POSITION int4 not null,
        BASECOST float8,
        FINALCOST float8,
        UMBBARCODE varchar(250),
        UMBDESCRIPTION varchar(250),
        UMCBARCODE varchar(250),
        UMCDESCRIPTION varchar(250),
        UMBXUMC float8,
        UMCCODE varchar(250),
        UMBCODE varchar(250),
        TOTALUNITS float8,
        TODELIVERYUNITS float8,
        RECEIVEDUNITS float8,
        PENDINGUNITS float8,
        TOTALNEED float8,
        TOTALTODELIVERY float8,
        TOTALRECEIVED float8,
        TOTALPENDING float8,
        TOLERANCE int4,
        INNERPACK varchar(255),
        CASEPACK varchar(255),
        ITEMDELIVERYDATE timestamp,
        COMMENT varchar(255),
        NORMALPRICE float8,
        OFFERPRICE float8,
        BARCODE1 varchar(255),
        BARCODE2 varchar(255),
        primary key (DVRORDER_ID, ITEM_ID, POSITION)
    );

    create table LOGISTICA.DVRORDERSTATE (
        ID  bigserial not null,
        WHEN1 timestamp not null,
        WHO varchar(255),
        DVRORDER_ID int8 not null,
        DVRORDERSTATETYPE_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.DVRORDERSTATETYPE (
        ID  bigserial not null,
        CODE varchar(250) not null,
        DESCRIPTION varchar(250) not null,
        VALID boolean not null,
        VISIBLE boolean not null,
        primary key (ID)
    );

    create table LOGISTICA.DVRPREDELIVERYDETAIL (
        DVRORDER_ID int8 not null,
        ITEM_ID int8 not null,
        LOCATION_ID int8 not null,
        POSITION int4 not null,
        TOTALUNITS float8,
        TODELIVERYUNITS float8,
        RECEIVEDUNITS float8,
        PENDINGUNITS float8,
        TOTALNEED float8,
        TOTALTODELIVERY float8,
        TOTALRECEIVED float8,
        TOTALPENDING float8,
        primary key (DVRORDER_ID, ITEM_ID, LOCATION_ID, POSITION)
    );

    create table LOGISTICA.ITEM (
        ID  bigserial not null,
        SKU varchar(250),
        NAME varchar(250),
        umc_cd_vendor float8,
        umc_location_vendor float8,
        umd_cd_location float8,
        umd_vendor_cd float8,
        umd_vendor_location float8,
        curve varchar(250),
        itemcode varchar(250),
        primary key (ID)
    );

    create table LOGISTICA.ITEMATTRIBUTE (
        ID  bigserial not null,
        attributetype varchar(250),
        code varchar(250),
        value varchar(250),
        ITEM_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.LOCATION (
        ID  bigserial not null,
        CODE varchar(250),
        NAME varchar(250),
        ADDRESS varchar(250),
        COMMUNE varchar(250),
        CITY varchar(250),
        PHONE varchar(250),
        WAREHOUSE boolean not null,
        PRIORITY int4,
        EMAIL varchar(250),
        GLN varchar(250),
        managername varchar(250),
        manageremail varchar(250),
        primary key (ID)
    );

    create table LOGISTICA.MODULE (
        ID  bigserial not null,
        NAME varchar(250) not null,
        starts timestamp not null,
        ends timestamp not null,
        visualorder int4 not null,
        primary key (ID)
    );

    create table LOGISTICA.NOTIFICATION (
        CONTACT_ID int8 not null,
        NOTIFICATIONTYPE_ID int8 not null,
        NOTIFICATIONTIME_ID int8 not null,
        primary key (CONTACT_ID, NOTIFICATIONTYPE_ID, NOTIFICATIONTIME_ID)
    );

    create table LOGISTICA.NOTIFICATIONDATA (
        ID  bigserial not null,
        NUMBER1 varchar(250),
        LOADDATE timestamp,
        NOTIFICATIONTYPE_ID int8 not null,
        VENDOR_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.NOTIFICATIONTIME (
        ID  bigserial not null,
        HOURNAME varchar(250),
        HOURDATE timestamp,
        VISIBLE boolean,
        primary key (ID)
    );

    create table LOGISTICA.NOTIFICATIONTYPE (
        ID  bigserial not null,
        CODE varchar(250),
        DESCRIPTION varchar(250),
        VISIBLE boolean,
        VISUALORDER int8,
        primary key (ID)
    );

    create table LOGISTICA.ORDER (
        ID  bigserial not null,
        NUMBER int8,
        EMITTED timestamp,
        creationdate timestamp,
        CURRENTSOASTATETYPEDATE timestamp,
        RESPONSABLE_ID int8 not null,
        RETAILER_ID int8 not null,
        VENDOR_ID int8 not null,
        SECTION_ID int8,
        ORDERTYPE_ID int8 not null,
        CLIENT_ID int8,
        CURRENTSOASTATETYPE_ID int8,
        primary key (ID)
    );

    create table LOGISTICA.ORDERATTRIBUTE (
        ID  bigserial not null,
        attributetype varchar(250),
        code varchar(250),
        value varchar(250),
        ORDER_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.ORDERTYPE (
        ID  bigserial not null,
        CODE varchar(250) not null,
        DESCRIPTION varchar(250) not null,
        primary key (ID)
    );

    create table LOGISTICA.PARAMETER (
        ID  bigserial not null,
        code varchar(250),
        description varchar(250),
        value varchar(250),
        unit varchar(250),
        visible boolean,
        active boolean,
        creationdate timestamp,
        modificationdate timestamp,
        primary key (ID)
    );

    create table LOGISTICA.PARAMETERCHANGE (
        ID  bigserial not null,
        WHEN1 timestamp,
        user1 varchar(255),
        usertype varchar(255),
        comment varchar(255),
        PARAMETER_ID int8 not null,
        primary key (ID)
    );

    create table LOGISTICA.PENDINGMAIL (
        ID  bigserial not null,
        WHEN1 timestamp not null,
        code varchar(255),
        mailsession varchar(255),
        subject varchar(255),
        frommail varchar(255),
        tomail varchar(255),
        ccmail varchar(255),
        ccomail varchar(255),
        content varchar not null,
        attempts int4 not null,
        lastattempt timestamp not null,
        status int4 not null,
        datetosend timestamp,
        PENDINGMAILTYPE_ID int8,
        primary key (ID)
    );

    create table LOGISTICA.PENDINGMAILTYPE (
        ID  bigserial not null,
        code varchar(255) not null,
        description varchar(255) not null,
        priority int4 not null,
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
        datatosend timestamp not null,
        headervalues varchar(255),
        PENDINGMESSAGETYPE_ID int8,
        primary key (ID)
    );

    create table LOGISTICA.PENDINGMESSAGETYPE (
        ID  bigserial not null,
        code varchar(255) not null,
        description varchar(255) not null,
        priority int4 not null,
        headerparameters varchar(255),
        primary key (ID)
    );

    create table LOGISTICA.PREDATINGRESOURCEGROUP (
        ID int8 not null,
        VENDOR_ID int8,
        primary key (ID)
    );

    create table LOGISTICA.RESERVE (
        ID  bigserial not null,
        WHEN1 timestamp,
        LOCATION_ID int8,
        primary key (ID)
    );

    create table LOGISTICA.RESERVEDETAIL (
        RESERVE_ID int8 not null,
        MODULE_ID int8 not null,
        DOCK_ID int8 not null,
        WHEN1 timestamp,
        primary key (RESERVE_ID, MODULE_ID, DOCK_ID)
    );

    create table LOGISTICA.RESOURCEBLOCKING (
        ID int8 not null,
        COMMENT varchar(250),
        BLOCKINGGROUP_ID int8,
        primary key (ID)
    );

    create table LOGISTICA.RESOURCEBLOCKINGGROUP (
        ID  bigserial not null,
        COMMENT varchar(250),
        CREATED timestamp,
        REASON varchar(250),
        RECURRENCE int4,
        SINCE timestamp,
        UNTIL timestamp,
        WHO varchar(250),
        LOCATION_ID int8,
        primary key (ID)
    );

    create table LOGISTICA.RESPONSABLE (
        ID  bigserial not null,
        CODE varchar(250) not null,
        NAME varchar(250),
        EMAIL varchar(250),
        primary key (ID)
    );

    create table LOGISTICA.RETAILER (
        ID  bigserial not null,
        CODE varchar(250) not null,
        DNI varchar(250),
        DESCRIPTION varchar(250),
        GLN varchar(250),
        primary key (ID)
    );

    create table LOGISTICA.SECTION (
        ID  bigserial not null,
        NAME varchar(255),
        CODE varchar(255),
        primary key (ID)
    );

    create table LOGISTICA.SOASTATE (
        ID  bigserial not null,
        WHEN1 timestamp not null,
        COMMENT varchar(255),
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

    create table LOGISTICA.TIMEMODULE (
        ID  bigserial not null,
        description varchar(255),
        visualorder int4,
        primary key (ID)
    );

    create table LOGISTICA.VENDOR (
        ID  bigserial not null,
        NAME varchar(250),
        CODE varchar(255) not null,
        DNI varchar(250),
        TRADENAME varchar(250),
        ADDRESS varchar(250),
        COMMUNE varchar(250),
        CITY varchar(250),
        GLN varchar(250),
        DOMESTIC boolean,
        EMAIL varchar(250),
        PHONE varchar(250),
        COUNTRY varchar(250),
        INTERNALCODE varchar(250),
        MINCORRELATIVE int4,
        primary key (ID)
    );

    create table LOGISTICA.VENDORITEM (
        ITEM_ID int8 not null,
        VENDOR_ID int8 not null,
        VENDORITEMCODE varchar(250),
        primary key (ITEM_ID, VENDOR_ID)
    );

    alter table LOGISTICA.ORDER 
        add constraint UK_496x63xlvuabemntdlihjmqoe unique (NUMBER);

    alter table LOGISTICA.RESERVEDETAIL 
        add constraint UNIQ_MODULE_DOCK_WHEN unique (DOCK_ID, MODULE_ID, WHEN1);

    alter table LOGISTICA.RETAILER 
        add constraint UK_cxgnewalfwunwa8fee14bot88 unique (CODE);

    alter table LOGISTICA.BULK 
        add constraint FKxcj9celgvbktxj8nri8bc3k9 
        foreign key (DVRDELIVERY_ID) 
        references LOGISTICA.DVRDELIVERY;

    alter table LOGISTICA.BULK 
        add constraint FKgk3drs7rbgn5eb8csmmmfcl6a 
        foreign key (DOCUMENT_ID) 
        references LOGISTICA.DOCUMENT;

    alter table LOGISTICA.BULKDETAIL 
        add constraint FKsgll4qwmkl9ypj6xr02wn91h3 
        foreign key (BULK_ID) 
        references LOGISTICA.BULK;

    alter table LOGISTICA.BULKDETAIL 
        add constraint FK8af96sjunrymmwuayx0da4kuc 
        foreign key (DVRORDER_ID, DVRDELIVERY_ID, ITEM_ID, LOCATION_ID, POSITION) 
        references LOGISTICA.DVRORDERDELIVERYDETAIL;

    alter table LOGISTICA.CHARGEDISCOUNTDVRORDER 
        add constraint FK13t1eb3iuxwjnlhyy55ihvfa6 
        foreign key (CHARGEDISCOUNT_ID) 
        references LOGISTICA.CHARGEDISCOUNT;

    alter table LOGISTICA.CHARGEDISCOUNTDVRORDER 
        add constraint FK9kctrhwleyc7ce3566cwhyfn1 
        foreign key (DVRORDER_ID) 
        references LOGISTICA.DVRORDER;

    alter table LOGISTICA.CHARGEDISCOUNTDVRORDERDETAIL 
        add constraint FK1r3rbwbj86gnt2trj69b27ulg 
        foreign key (CHARGEDISCOUNT_ID) 
        references LOGISTICA.CHARGEDISCOUNT;

    alter table LOGISTICA.CHARGEDISCOUNTDVRORDERDETAIL 
        add constraint FKok5x4wrn390revu6qovpjesnu 
        foreign key (DVRORDER_ID, ITEM_ID, POSITION) 
        references LOGISTICA.DVRORDERDETAIL;

    alter table LOGISTICA.CONTACT 
        add constraint FK56xsqrju614xnsga80t93srbq 
        foreign key (VENDOR_ID) 
        references LOGISTICA.VENDOR;

    alter table LOGISTICA.DATING 
        add constraint FKkcysqoew1jk6w8vtqojxxtpo3 
        foreign key (ID) 
        references LOGISTICA.RESERVE;

    alter table LOGISTICA.DATING 
        add constraint FKkc89gk4eed2qnxxu2130l2p6m 
        foreign key (VENDOR_ID) 
        references LOGISTICA.VENDOR;

    alter table LOGISTICA.DATING 
        add constraint FKoc2nhlb127g6jma0gms3brcbf 
        foreign key (DVRDELIVERY_ID) 
        references LOGISTICA.DVRDELIVERY;

    alter table LOGISTICA.DATINGREQUEST 
        add constraint FKb4aoief8vtrtxp59wxt7t5e1t 
        foreign key (DVRDELIVERY_ID) 
        references LOGISTICA.DVRDELIVERY;

    alter table LOGISTICA.DATINGRESOURCE 
        add constraint FKeqyhi83cvi8sb1xeoleqpf6ky 
        foreign key (DOCK_ID) 
        references LOGISTICA.DOCK;

    alter table LOGISTICA.DATINGRESOURCE 
        add constraint FK2evrclerme12rntqpjffer6lj 
        foreign key (MODULE_ID) 
        references LOGISTICA.MODULE;

    alter table LOGISTICA.DDCDELIVERY 
        add constraint FKfefyyjykndyo9pii6vk2erjdk 
        foreign key (DDCORDER_ID) 
        references LOGISTICA.DDCORDER;

    alter table LOGISTICA.DDCDELIVERY 
        add constraint FKm7gw8q8m0n7r0agblu90lject 
        foreign key (CURRENTSTATETYPE_ID) 
        references LOGISTICA.DDCDELIVERYSTATETYPE;

    alter table LOGISTICA.DDCDELIVERY 
        add constraint FKl8fcwnvffbq1d8moosj2l5h8k 
        foreign key (VENDOR_ID) 
        references LOGISTICA.VENDOR;

    alter table LOGISTICA.DDCDELIVERYDETAIL 
        add constraint FKkxb2lbvv73vejsr1qt7a5nkiy 
        foreign key (DDCDELIVERY_ID) 
        references LOGISTICA.DDCDELIVERY;

    alter table LOGISTICA.DDCDELIVERYDETAIL 
        add constraint FK47ttsyrse15nb24mqr770dbcm 
        foreign key (ITEM_ID) 
        references LOGISTICA.ITEM;

    alter table LOGISTICA.DDCDELIVERYSTATE 
        add constraint FK6kp1qnv18f6f2y5kkywapqxj6 
        foreign key (DDCDELIVERY_ID) 
        references LOGISTICA.DDCDELIVERY;

    alter table LOGISTICA.DDCDELIVERYSTATE 
        add constraint FKorekv9092uxfp1nwocy211oxh 
        foreign key (DDCDELIVERYSTATETYPE_ID) 
        references LOGISTICA.DDCDELIVERYSTATETYPE;

    alter table LOGISTICA.DDCFILE 
        add constraint FKf5bbwh3goo7x8j5jsy9wavneg 
        foreign key (DDCORDER_ID) 
        references LOGISTICA.DDCORDER;

    alter table LOGISTICA.DDCORDER 
        add constraint FKogywxo9f29c2hiaxmx15q6ia4 
        foreign key (ID) 
        references LOGISTICA.ORDER;

    alter table LOGISTICA.DDCORDER 
        add constraint FKcssqrefe67fsorgg5m8xixws7 
        foreign key (CURRENTSTATETYPE_ID) 
        references LOGISTICA.DDCORDERSTATETYPE;

    alter table LOGISTICA.DDCORDER 
        add constraint FKk26euxxfdadxt198rrt2o2flw 
        foreign key (SALELOCATION_ID) 
        references LOGISTICA.LOCATION;

    alter table LOGISTICA.DDCORDER 
        add constraint FKht70gvdjovou7gir4lv6r0vat 
        foreign key (CURRENTDDCDELIVERY_ID) 
        references LOGISTICA.DDCDELIVERY;

    alter table LOGISTICA.DDCORDERCHARGEDISCOUNT 
        add constraint FKdrfv5lw3gy8qtr976xo3avpj0 
        foreign key (DDCCHARGEDISCOUNT_ID) 
        references LOGISTICA.DDCCHARGEDISCOUNT;

    alter table LOGISTICA.DDCORDERCHARGEDISCOUNT 
        add constraint FKtplwl5xaeg66s6iyk07h39pfg 
        foreign key (DDCORDER_ID) 
        references LOGISTICA.DDCORDER;

    alter table LOGISTICA.DDCORDERCHARGEDISCOUNTDETAIL 
        add constraint FK6x2r1ce9fsw20l156iq50s0do 
        foreign key (DDCCHARGEDISCOUNT_ID) 
        references LOGISTICA.DDCCHARGEDISCOUNT;

    alter table LOGISTICA.DDCORDERCHARGEDISCOUNTDETAIL 
        add constraint FK3ue05ttqhhdpjl798inh1tfmh 
        foreign key (DDCORDER_ID, ITEM_ID, POSITION) 
        references LOGISTICA.DDCORDERDETAIL;

    alter table LOGISTICA.DDCORDERDETAIL 
        add constraint FK3vh06gu1wfwowi4a90xf1tev2 
        foreign key (DDCORDER_ID) 
        references LOGISTICA.DDCORDER;

    alter table LOGISTICA.DDCORDERDETAIL 
        add constraint FKi9n5e73l1cfjbj7sfhw9cpp7o 
        foreign key (ITEM_ID) 
        references LOGISTICA.ITEM;

    alter table LOGISTICA.DDCORDERSTATE 
        add constraint FKf6i9hjhxccojvi5psnrqb6hqo 
        foreign key (DDCORDER_ID) 
        references LOGISTICA.DDCORDER;

    alter table LOGISTICA.DDCORDERSTATE 
        add constraint FKqwlwbwfumqju2tjs1p9nbqlx8 
        foreign key (DDCORDERSTATETYPE_ID) 
        references LOGISTICA.DDCORDERSTATETYPE;

    alter table LOGISTICA.DOCK 
        add constraint FKg16b9wxrymup70tpaj1u615qw 
        foreign key (LOCATION_ID) 
        references LOGISTICA.LOCATION;

    alter table LOGISTICA.DOCUMENT 
        add constraint FK92w3pgt1jg9v6vmppy6fc5622 
        foreign key (DOCUMENTTYPE_ID) 
        references LOGISTICA.DOCUMENTTYPE;

    alter table LOGISTICA.DOCUMENTSTATE 
        add constraint FKskkyxaor83cd502tehc89i3ls 
        foreign key (DOCUMENT_ID) 
        references LOGISTICA.DOCUMENT;

    alter table LOGISTICA.DVRDELIVERY 
        add constraint FK6rbsm18h0e26v7l7y8ixjqhc0 
        foreign key (CURRENTSTATETYPE_ID) 
        references LOGISTICA.DVRDELIVERYSTATETYPE;

    alter table LOGISTICA.DVRDELIVERY 
        add constraint FKs4m6f9w0ceaacega8ttwr8s50 
        foreign key (VENDOR_ID) 
        references LOGISTICA.VENDOR;

    alter table LOGISTICA.DVRDELIVERY 
        add constraint FKtngl0i1hjdke1h58cl1puprnw 
        foreign key (DELIVERYLOCATION_ID) 
        references LOGISTICA.LOCATION;

    alter table LOGISTICA.DVRDELIVERYSTATE 
        add constraint FKrsbc7afd6cv9bjfxwy3fhje65 
        foreign key (DVRDELIVERY_ID) 
        references LOGISTICA.DVRDELIVERY;

    alter table LOGISTICA.DVRDELIVERYSTATE 
        add constraint FK7qc5dx2vouuor5ltiq7r3yicr 
        foreign key (DVRDELIVERYSTATETYPE_ID) 
        references LOGISTICA.DVRDELIVERYSTATETYPE;

    alter table LOGISTICA.DVRORDER 
        add constraint FKkt3bbjplt8905kor8bkn6hxer 
        foreign key (ID) 
        references LOGISTICA.ORDER;

    alter table LOGISTICA.DVRORDER 
        add constraint FKayu9203g522ypkbc6c1stjr7j 
        foreign key (CURRENTSTATETYPE_ID) 
        references LOGISTICA.DVRORDERSTATETYPE;

    alter table LOGISTICA.DVRORDER 
        add constraint FK37ksbfu4txiyupleygyyaf8r2 
        foreign key (DELIVERYLOCATION_ID) 
        references LOGISTICA.LOCATION;

    alter table LOGISTICA.DVRORDER 
        add constraint FK67r18rxpfydst33950hx3fslg 
        foreign key (SALELOCATION_ID) 
        references LOGISTICA.LOCATION;

    alter table LOGISTICA.DVRORDERDELIVERY 
        add constraint FK8r0bc9lf27p88os8p0gtqgtuj 
        foreign key (DVRDELIVERY_ID) 
        references LOGISTICA.DVRDELIVERY;

    alter table LOGISTICA.DVRORDERDELIVERY 
        add constraint FKa6ayb12a6u0ltis36lb22bmfr 
        foreign key (DVRORDER_ID) 
        references LOGISTICA.DVRORDER;

    alter table LOGISTICA.DVRORDERDELIVERYDETAIL 
        add constraint FKo2a94vg0b95ksdiqnf6p2xp9o 
        foreign key (DVRORDER_ID, DVRDELIVERY_ID) 
        references LOGISTICA.DVRORDERDELIVERY;

    alter table LOGISTICA.DVRORDERDELIVERYDETAIL 
        add constraint FKkfw7alqc2pxwnetlfb7gh3rrh 
        foreign key (DVRORDER_ID, ITEM_ID, LOCATION_ID, POSITION) 
        references LOGISTICA.DVRPREDELIVERYDETAIL;

    alter table LOGISTICA.DVRORDERDETAIL 
        add constraint FKesxnf33s3x39guiur043oj39f 
        foreign key (DVRORDER_ID) 
        references LOGISTICA.DVRORDER;

    alter table LOGISTICA.DVRORDERDETAIL 
        add constraint FKjprkx9s13e180glktggdlgw4d 
        foreign key (ITEM_ID) 
        references LOGISTICA.ITEM;

    alter table LOGISTICA.DVRORDERSTATE 
        add constraint FKsguol1cq0e8mwk0my3skubc3u 
        foreign key (DVRORDER_ID) 
        references LOGISTICA.DVRORDER;

    alter table LOGISTICA.DVRORDERSTATE 
        add constraint FKob7wbp0wl8cbiesc69qexfun5 
        foreign key (DVRORDERSTATETYPE_ID) 
        references LOGISTICA.DVRORDERSTATETYPE;

    alter table LOGISTICA.DVRPREDELIVERYDETAIL 
        add constraint FKkd9roja5rpnxa8dw4wip0fimm 
        foreign key (DVRORDER_ID, ITEM_ID, POSITION) 
        references LOGISTICA.DVRORDERDETAIL;

    alter table LOGISTICA.DVRPREDELIVERYDETAIL 
        add constraint FKjg7k2ukw758295a9r9nkv2wxj 
        foreign key (LOCATION_ID) 
        references LOGISTICA.LOCATION;

    alter table LOGISTICA.ITEMATTRIBUTE 
        add constraint FKmq73u9suj4kusko6lvatwljm1 
        foreign key (ITEM_ID) 
        references LOGISTICA.ITEM;

    alter table LOGISTICA.NOTIFICATION 
        add constraint FK2vjdnsoq074wmbkjn3yelxltw 
        foreign key (CONTACT_ID) 
        references LOGISTICA.CONTACT;

    alter table LOGISTICA.NOTIFICATION 
        add constraint FKt16pk676b643qeqh2gkwsew7e 
        foreign key (NOTIFICATIONTYPE_ID) 
        references LOGISTICA.NOTIFICATIONTYPE;

    alter table LOGISTICA.NOTIFICATION 
        add constraint FKcmadd6lxioy6tfjaktvsy3deu 
        foreign key (NOTIFICATIONTIME_ID) 
        references LOGISTICA.NOTIFICATIONTIME;

    alter table LOGISTICA.NOTIFICATIONDATA 
        add constraint FKgqolivaluxt67t27swbo89p7y 
        foreign key (NOTIFICATIONTYPE_ID) 
        references LOGISTICA.NOTIFICATIONTYPE;

    alter table LOGISTICA.NOTIFICATIONDATA 
        add constraint FKj41j7sd0xxws7twbkf9ccpv5a 
        foreign key (VENDOR_ID) 
        references LOGISTICA.VENDOR;

    alter table LOGISTICA.ORDER 
        add constraint FKc6fuhjn33ratdgg70r8k9x45s 
        foreign key (RESPONSABLE_ID) 
        references LOGISTICA.RESPONSABLE;

    alter table LOGISTICA.ORDER 
        add constraint FKd4f61dw9m2kcebwl06ji8bjiq 
        foreign key (RETAILER_ID) 
        references LOGISTICA.RETAILER;

    alter table LOGISTICA.ORDER 
        add constraint FK3k8w42xdsqn89q0bkoh3r77s1 
        foreign key (VENDOR_ID) 
        references LOGISTICA.VENDOR;

    alter table LOGISTICA.ORDER 
        add constraint FKmb5it5mcyht63qfojh76otbfu 
        foreign key (SECTION_ID) 
        references LOGISTICA.SECTION;

    alter table LOGISTICA.ORDER 
        add constraint FKmyjb6jr01p5x7pucxb8by81oo 
        foreign key (ORDERTYPE_ID) 
        references LOGISTICA.ORDERTYPE;

    alter table LOGISTICA.ORDER 
        add constraint FKgfx8euilrluqacf2359sck10n 
        foreign key (CLIENT_ID) 
        references LOGISTICA.CLIENT;

    alter table LOGISTICA.ORDER 
        add constraint FKlatteksoasax159bc6yi1vsaj 
        foreign key (CURRENTSOASTATETYPE_ID) 
        references LOGISTICA.SOASTATETYPE;

    alter table LOGISTICA.ORDERATTRIBUTE 
        add constraint FKhjespfw9l2dy90n18yrvc1dnm 
        foreign key (ORDER_ID) 
        references LOGISTICA.ORDER;

    alter table LOGISTICA.PARAMETERCHANGE 
        add constraint FKo15tuadv501i40i3b2n00wt81 
        foreign key (PARAMETER_ID) 
        references LOGISTICA.PARAMETER;

    alter table LOGISTICA.PENDINGMAIL 
        add constraint FKfch7i1a4gort6iwjo86y65855 
        foreign key (PENDINGMAILTYPE_ID) 
        references LOGISTICA.PENDINGMAILTYPE;

    alter table LOGISTICA.PENDINGMESSAGE 
        add constraint FK3mx7pp53on5c8tw9dkix56x2y 
        foreign key (PENDINGMESSAGETYPE_ID) 
        references LOGISTICA.PENDINGMESSAGETYPE;

    alter table LOGISTICA.PREDATINGRESOURCEGROUP 
        add constraint FKjopk69bx2uottvj07offe7yln 
        foreign key (ID) 
        references LOGISTICA.RESOURCEBLOCKINGGROUP;

    alter table LOGISTICA.PREDATINGRESOURCEGROUP 
        add constraint FK6g3ol3y0fp38alits22fsh6mx 
        foreign key (VENDOR_ID) 
        references LOGISTICA.VENDOR;

    alter table LOGISTICA.RESERVE 
        add constraint FK72oqn24tk8txemllelocfokmw 
        foreign key (LOCATION_ID) 
        references LOGISTICA.LOCATION;

    alter table LOGISTICA.RESERVEDETAIL 
        add constraint FKantrp9b7qtlkd9gh5fqfsgrjp 
        foreign key (DOCK_ID, MODULE_ID) 
        references LOGISTICA.DATINGRESOURCE;

    alter table LOGISTICA.RESERVEDETAIL 
        add constraint FKpb1nhe6r106rpx2d0s1o6738s 
        foreign key (RESERVE_ID) 
        references LOGISTICA.RESERVE;

    alter table LOGISTICA.RESOURCEBLOCKING 
        add constraint FK2cxtinnpmh097i47a8i57vlvf 
        foreign key (ID) 
        references LOGISTICA.RESERVE;

    alter table LOGISTICA.RESOURCEBLOCKING 
        add constraint FKfdolamws3qoxw9vmtyy6glq48 
        foreign key (BLOCKINGGROUP_ID) 
        references LOGISTICA.RESOURCEBLOCKINGGROUP;

    alter table LOGISTICA.RESOURCEBLOCKINGGROUP 
        add constraint FK65qnr4obfqt412fu3ho91sjmy 
        foreign key (LOCATION_ID) 
        references LOGISTICA.LOCATION;

    alter table LOGISTICA.SOASTATE 
        add constraint FKf0p73w0ga80llgkjt9dxshl5e 
        foreign key (ORDER_ID) 
        references LOGISTICA.ORDER;

    alter table LOGISTICA.SOASTATE 
        add constraint FKn05b4ogjbanrcutcn353157f7 
        foreign key (SOASTATETYPE_ID) 
        references LOGISTICA.SOASTATETYPE;

    alter table LOGISTICA.VENDORITEM 
        add constraint FK3yqtnnpel9mrntl9br9dwi9vs 
        foreign key (ITEM_ID) 
        references LOGISTICA.ITEM;

    alter table LOGISTICA.VENDORITEM 
        add constraint FKemm5d2uexdhuur7u0wn6r2v2j 
        foreign key (VENDOR_ID) 
        references LOGISTICA.VENDOR;
