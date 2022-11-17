create table public.resume (
  uuid character(36) primary key not null,
  full_name text
);

create table contact
(
    id          serial constraint contact_pk primary key,
    resume_uuid char(36) not null constraint contact_resume_uuid_fk
                    references resume on delete cascade,
    type        text     not null,
    value       text     not null
);

alter table contact owner to postgres;

create unique index contact_uuid_type_index
    on contact (resume_uuid, type);

create table section
(
    id          serial primary key,
    resume_uuid char(36) not null references resume (uuid) on delete cascade,
    type        text     not null,
    content       text     not null
);
create unique index section_uuid_type_index
    on section (resume_uuid, type);