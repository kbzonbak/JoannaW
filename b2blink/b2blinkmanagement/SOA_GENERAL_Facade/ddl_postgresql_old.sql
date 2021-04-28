
    alter table public.ACCESS 
        drop constraint FK72BAA964D3C89F69;

    alter table public.ACCESS 
        drop constraint FK72BAA964A1DED1ED;

    alter table public.CONTRACTED_SERVICE 
        drop constraint FKBB7A4587352FC170;

    alter table public.CONTRACTED_SERVICE 
        drop constraint FKBB7A4587D3C89F69;

    alter table public.CONTRACTED_SERVICE 
        drop constraint FKBB7A45878D7C810E;

    alter table public.CONTRACTED_SERVICE 
        drop constraint FKBB7A4587A1DED1ED;

    alter table public.CONTRACTED_SERVICE 
        drop constraint FKBB7A4587751CB327;

    alter table public.MESSAGEFOLDER 
        drop constraint FK7D11D315D3C89F69;

    alter table public.MESSAGEFORMAT 
        drop constraint FK7D14AE9E751CB327;

    alter table public.SERVICE_EVENT 
        drop constraint FK88DB2C50D3C89F69;

    alter table public.SERVICE_EVENT 
        drop constraint FK88DB2C50A1DED1ED;

    alter table public.SERVICE_EVENT 
        drop constraint FK88DB2C50751CB327;

    alter table public.SITE_SERVICE 
        drop constraint FK3FF9999DA1DED1ED;

    alter table public.SITE_SERVICE 
        drop constraint FK3FF9999D751CB327;

    alter table public.USER 
        drop constraint FK27E3CB2F61BACB;

    alter table public.USERTYPE_FUNCTIONALITY 
        drop constraint FK5562B671D5AC1D69;

    alter table public.USERTYPE_FUNCTIONALITY 
        drop constraint FK5562B6712F61BACB;

    alter table public.USER_COMPANY 
        drop constraint FK19DB57E9D3C89F69;

    alter table public.USER_COMPANY 
        drop constraint FK19DB57E9F039896B;

    drop table public.ACCESS;

    drop table public.COMPANY;

    drop table public.CONTRACTED_SERVICE;

    drop table public.FUNCTIONALITY;

    drop table public.MESSAGEFOLDER;

    drop table public.MESSAGEFORMAT;

    drop table public.SERVICE;

    drop table public.SERVICE_EVENT;

    drop table public.SITE;

    drop table public.SITE_SERVICE;

    drop table public.USER;

    drop table public.USERTYPE;

    drop table public.USERTYPE_FUNCTIONALITY;

    drop table public.USER_COMPANY;

    create table public.ACCESS (
        COMPANY_ID int8 not null,
        SITE_ID int8 not null,
        NAME varchar(128),
        CODE varchar(128),
        primary key (COMPANY_ID, SITE_ID)
    );

    create table public.COMPANY (
        ID  bigserial not null,
        NAME varchar(128),
        RUT varchar(9),
        primary key (ID)
    );

    create table public.CONTRACTED_SERVICE (
        SITE_ID int8 not null,
        SERVICE_ID int8 not null,
        COMPANY_ID int8 not null,
        ACTIVE bool,
        LAST_MSG timestamp,
        FOLDER_ID int8 not null,
        FORMAT_ID int8 not null,
        primary key (SITE_ID, SERVICE_ID, COMPANY_ID)
    );

    create table public.FUNCTIONALITY (
        ID  bigserial not null,
        NAME varchar(128),
        DESCRIPTION varchar(128),
        primary key (ID)
    );

    create table public.MESSAGEFOLDER (
        ID  bigserial not null,
        PATH varchar(512),
        COMPANY_ID int8 not null,
        primary key (ID)
    );

    create table public.MESSAGEFORMAT (
        ID  bigserial not null,
        NAME varchar(128),
        SERVICE_ID int8 not null,
        primary key (ID)
    );

    create table public.SERVICE (
        ID  bigserial not null,
        NAME varchar(128),
        primary key (ID)
    );

    create table public.SERVICE_EVENT (
        ID  bigserial not null,
        DATE_CREATED timestamp not null,
        DATE_PROCESSED timestamp,
        PROCESSED bool not null,
        SITE_ID int8 not null,
        SERVICE_ID int8 not null,
        COMPANY_ID int8 not null,
        primary key (ID)
    );

    create table public.SITE (
        ID  bigserial not null,
        NAME varchar(128),
        CREATED timestamp,
        primary key (ID)
    );

    create table public.SITE_SERVICE (
        SITE_ID int8 not null,
        SERVICE_ID int8 not null,
        ACTIVE bool,
        primary key (SITE_ID, SERVICE_ID)
    );

    create table public.USER (
        ID  bigserial not null,
        ACTIVE bool,
        RUT varchar(9),
        NAME varchar(128),
        LASTNAME varchar(128),
        EMAIL varchar(64),
        PASSWORD varchar(128) not null,
        BLOCKED bool,
        CHECKVOLATILE int4,
        LASTACCESS timestamp,
        LASTLOGON timestamp,
        LASTPASSCHANGE timestamp,
        QUESTION varchar(128),
        ANSWER varchar(128),
        SESSIONID varchar(128),
        TRIESCOUNT int4,
        USERTYPE_ID int8 not null,
        primary key (ID)
    );

    create table public.USERTYPE (
        ID  bigserial not null,
        NAME varchar(128),
        DESCRIPTION varchar(128),
        primary key (ID)
    );

    create table public.USERTYPE_FUNCTIONALITY (
        USERTYPE_ID int8 not null,
        FUNCTIONALITY_ID int8 not null,
        primary key (USERTYPE_ID, FUNCTIONALITY_ID)
    );

    create table public.USER_COMPANY (
        COMPANY_ID int8 not null,
        USER_ID int8 not null,
        ACTIVE bool,
        primary key (COMPANY_ID, USER_ID)
    );

    alter table public.ACCESS 
        add constraint FK72BAA964D3C89F69 
        foreign key (COMPANY_ID) 
        references public.COMPANY;

    alter table public.ACCESS 
        add constraint FK72BAA964A1DED1ED 
        foreign key (SITE_ID) 
        references public.SITE;

    alter table public.CONTRACTED_SERVICE 
        add constraint FKBB7A4587352FC170 
        foreign key (FOLDER_ID) 
        references public.MESSAGEFOLDER;

    alter table public.CONTRACTED_SERVICE 
        add constraint FKBB7A4587D3C89F69 
        foreign key (COMPANY_ID) 
        references public.COMPANY;

    alter table public.CONTRACTED_SERVICE 
        add constraint FKBB7A45878D7C810E 
        foreign key (FORMAT_ID) 
        references public.MESSAGEFORMAT;

    alter table public.CONTRACTED_SERVICE 
        add constraint FKBB7A4587A1DED1ED 
        foreign key (SITE_ID) 
        references public.SITE;

    alter table public.CONTRACTED_SERVICE 
        add constraint FKBB7A4587751CB327 
        foreign key (SERVICE_ID) 
        references public.SERVICE;

    alter table public.MESSAGEFOLDER 
        add constraint FK7D11D315D3C89F69 
        foreign key (COMPANY_ID) 
        references public.COMPANY;

    alter table public.MESSAGEFORMAT 
        add constraint FK7D14AE9E751CB327 
        foreign key (SERVICE_ID) 
        references public.SERVICE;

    alter table public.SERVICE_EVENT 
        add constraint FK88DB2C50D3C89F69 
        foreign key (COMPANY_ID) 
        references public.COMPANY;

    alter table public.SERVICE_EVENT 
        add constraint FK88DB2C50A1DED1ED 
        foreign key (SITE_ID) 
        references public.SITE;

    alter table public.SERVICE_EVENT 
        add constraint FK88DB2C50751CB327 
        foreign key (SERVICE_ID) 
        references public.SERVICE;

    alter table public.SITE_SERVICE 
        add constraint FK3FF9999DA1DED1ED 
        foreign key (SITE_ID) 
        references public.SITE;

    alter table public.SITE_SERVICE 
        add constraint FK3FF9999D751CB327 
        foreign key (SERVICE_ID) 
        references public.SERVICE;

    alter table public.USER 
        add constraint FK27E3CB2F61BACB 
        foreign key (USERTYPE_ID) 
        references public.USERTYPE;

    alter table public.USERTYPE_FUNCTIONALITY 
        add constraint FK5562B671D5AC1D69 
        foreign key (FUNCTIONALITY_ID) 
        references public.FUNCTIONALITY;

    alter table public.USERTYPE_FUNCTIONALITY 
        add constraint FK5562B6712F61BACB 
        foreign key (USERTYPE_ID) 
        references public.USERTYPE;

    alter table public.USER_COMPANY 
        add constraint FK19DB57E9D3C89F69 
        foreign key (COMPANY_ID) 
        references public.COMPANY;

    alter table public.USER_COMPANY 
        add constraint FK19DB57E9F039896B 
        foreign key (USER_ID) 
        references public.USER;
