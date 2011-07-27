
    alter table Game 
        drop 
        foreign key FK21C01290F9D3FC;

    alter table Game 
        drop 
        foreign key FK21C0122641DEFE;

    alter table Game 
        drop 
        foreign key FK21C0122BB4F78D;

    alter table League 
        drop 
        foreign key FK876D3E4F3DF53C96;

    alter table Season 
        drop 
        foreign key FK935F5703F8470B7E;

    alter table Team_Season 
        drop 
        foreign key FKD9A1C705D94DE0D1;

    alter table Team_Season 
        drop 
        foreign key FKD9A1C705FA0EADBE;

    drop table if exists Game;

    drop table if exists League;

    drop table if exists Season;

    drop table if exists Sport;

    drop table if exists Team;

    drop table if exists Team_Season;

    create table Game (
        id bigint not null auto_increment,
        date tinyblob,
        jr integer not null,
        played bit not null,
        scoreAway integer not null,
        scoreHome integer not null,
        away_id bigint,
        home_id bigint,
        season_id bigint,
        primary key (id)
    );

    create table League (
        id bigint not null auto_increment,
        name varchar(255),
        sport_id bigint,
        primary key (id)
    );

    create table Season (
        id bigint not null auto_increment,
        ended bit not null,
        name varchar(255),
        nbClubs integer not null,
        started bit not null,
        league_id bigint,
        primary key (id)
    );

    create table Sport (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    );

    create table Team (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    );

    create table Team_Season (
        Team_id bigint not null,
        seasons_id bigint not null
    );

    alter table Game 
        add index FK21C01290F9D3FC (home_id), 
        add constraint FK21C01290F9D3FC 
        foreign key (home_id) 
        references Team (id);

    alter table Game 
        add index FK21C0122641DEFE (season_id), 
        add constraint FK21C0122641DEFE 
        foreign key (season_id) 
        references Season (id);

    alter table Game 
        add index FK21C0122BB4F78D (away_id), 
        add constraint FK21C0122BB4F78D 
        foreign key (away_id) 
        references Team (id);

    alter table League 
        add index FK876D3E4F3DF53C96 (sport_id), 
        add constraint FK876D3E4F3DF53C96 
        foreign key (sport_id) 
        references Sport (id);

    alter table Season 
        add index FK935F5703F8470B7E (league_id), 
        add constraint FK935F5703F8470B7E 
        foreign key (league_id) 
        references League (id);

    alter table Team_Season 
        add index FKD9A1C705D94DE0D1 (seasons_id), 
        add constraint FKD9A1C705D94DE0D1 
        foreign key (seasons_id) 
        references Season (id);

    alter table Team_Season 
        add index FKD9A1C705FA0EADBE (Team_id), 
        add constraint FKD9A1C705FA0EADBE 
        foreign key (Team_id) 
        references Team (id);
