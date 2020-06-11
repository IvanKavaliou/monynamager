PGDMP     !                    x           moneyman    10.9    10.9 )    *           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            +           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            ,           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false            -           1262    18666    moneyman    DATABASE     �   CREATE DATABASE moneyman WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Russian_Russia.1251' LC_CTYPE = 'Russian_Russia.1251';
    DROP DATABASE moneyman;
             user    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false            .           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    3                        3079    12924    plpgsql 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
    DROP EXTENSION plpgsql;
                  false            /           0    0    EXTENSION plpgsql    COMMENT     @   COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
                       false    1            �            1259    19475 
   global_seq    SEQUENCE     x   CREATE SEQUENCE public.global_seq
    START WITH 100000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.global_seq;
       public       postgres    false    3            �            1259    19477    currency    TABLE     �   CREATE TABLE public.currency (
    id integer DEFAULT nextval('public.global_seq'::regclass) NOT NULL,
    code character varying(3) NOT NULL
);
    DROP TABLE public.currency;
       public         postgres    false    196    3            �            1259    19529    transaction    TABLE     �  CREATE TABLE public.transaction (
    id integer DEFAULT nextval('public.global_seq'::regclass) NOT NULL,
    id_users integer NOT NULL,
    id_currency integer NOT NULL,
    value numeric NOT NULL,
    id_transaction_category integer NOT NULL,
    date timestamp without time zone DEFAULT '2020-04-04 12:41:12.00025'::timestamp without time zone NOT NULL,
    name character varying(100) NOT NULL
);
    DROP TABLE public.transaction;
       public         postgres    false    196    3            �            1259    19513    transaction_category    TABLE     �   CREATE TABLE public.transaction_category (
    id integer DEFAULT nextval('public.global_seq'::regclass) NOT NULL,
    name character varying(100) NOT NULL,
    id_users integer NOT NULL,
    id_transaction_types integer NOT NULL
);
 (   DROP TABLE public.transaction_category;
       public         postgres    false    196    3            �            1259    19507    transaction_types    TABLE     �   CREATE TABLE public.transaction_types (
    id integer DEFAULT nextval('public.global_seq'::regclass) NOT NULL,
    code character varying(10) NOT NULL
);
 %   DROP TABLE public.transaction_types;
       public         postgres    false    196    3            �            1259    19497 
   user_roles    TABLE     m   CREATE TABLE public.user_roles (
    id_users integer NOT NULL,
    roles character varying(255) NOT NULL
);
    DROP TABLE public.user_roles;
       public         postgres    false    3            �            1259    19483    users    TABLE     �  CREATE TABLE public.users (
    id integer DEFAULT nextval('public.global_seq'::regclass) NOT NULL,
    email character varying(100) NOT NULL,
    password character varying(30) NOT NULL,
    registred timestamp without time zone DEFAULT '2020-04-04 12:41:12.00025'::timestamp without time zone NOT NULL,
    enabled boolean DEFAULT true NOT NULL,
    id_currency integer NOT NULL
);
    DROP TABLE public.users;
       public         postgres    false    196    3            �            1259    19554    users_currency    TABLE     h   CREATE TABLE public.users_currency (
    id_users integer NOT NULL,
    id_currency integer NOT NULL
);
 "   DROP TABLE public.users_currency;
       public         postgres    false    3            !          0    19477    currency 
   TABLE DATA               ,   COPY public.currency (id, code) FROM stdin;
    public       postgres    false    197   32       &          0    19529    transaction 
   TABLE DATA               l   COPY public.transaction (id, id_users, id_currency, value, id_transaction_category, date, name) FROM stdin;
    public       postgres    false    202   n2       %          0    19513    transaction_category 
   TABLE DATA               X   COPY public.transaction_category (id, name, id_users, id_transaction_types) FROM stdin;
    public       postgres    false    201   �3       $          0    19507    transaction_types 
   TABLE DATA               5   COPY public.transaction_types (id, code) FROM stdin;
    public       postgres    false    200   �4       #          0    19497 
   user_roles 
   TABLE DATA               5   COPY public.user_roles (id_users, roles) FROM stdin;
    public       postgres    false    199   �4       "          0    19483    users 
   TABLE DATA               U   COPY public.users (id, email, password, registred, enabled, id_currency) FROM stdin;
    public       postgres    false    198   5       '          0    19554    users_currency 
   TABLE DATA               ?   COPY public.users_currency (id_users, id_currency) FROM stdin;
    public       postgres    false    203   �5       0           0    0 
   global_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.global_seq', 100046, true);
            public       postgres    false    196            �
           2606    19482    currency currency_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.currency
    ADD CONSTRAINT currency_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.currency DROP CONSTRAINT currency_pkey;
       public         postgres    false    197            �
           2606    19518 .   transaction_category transaction_category_pkey 
   CONSTRAINT     l   ALTER TABLE ONLY public.transaction_category
    ADD CONSTRAINT transaction_category_pkey PRIMARY KEY (id);
 X   ALTER TABLE ONLY public.transaction_category DROP CONSTRAINT transaction_category_pkey;
       public         postgres    false    201            �
           2606    19538    transaction transaction_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.transaction DROP CONSTRAINT transaction_pkey;
       public         postgres    false    202            �
           2606    19512 (   transaction_types transaction_types_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.transaction_types
    ADD CONSTRAINT transaction_types_pkey PRIMARY KEY (id);
 R   ALTER TABLE ONLY public.transaction_types DROP CONSTRAINT transaction_types_pkey;
       public         postgres    false    200            �
           2606    19501    user_roles user_roles_idx 
   CONSTRAINT     _   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_idx UNIQUE (id_users, roles);
 C   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT user_roles_idx;
       public         postgres    false    199    199            �
           2606    19558 !   users_currency users_currency_idx 
   CONSTRAINT     m   ALTER TABLE ONLY public.users_currency
    ADD CONSTRAINT users_currency_idx UNIQUE (id_users, id_currency);
 K   ALTER TABLE ONLY public.users_currency DROP CONSTRAINT users_currency_idx;
       public         postgres    false    203    203            �
           2606    19490    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public         postgres    false    198            �
           1259    19496    users_unique_email_idx    INDEX     P   CREATE UNIQUE INDEX users_unique_email_idx ON public.users USING btree (email);
 *   DROP INDEX public.users_unique_email_idx;
       public         postgres    false    198            �
           2606    19519 C   transaction_category transaction_category_id_transaction_types_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.transaction_category
    ADD CONSTRAINT transaction_category_id_transaction_types_fkey FOREIGN KEY (id_transaction_types) REFERENCES public.transaction_types(id) ON DELETE CASCADE;
 m   ALTER TABLE ONLY public.transaction_category DROP CONSTRAINT transaction_category_id_transaction_types_fkey;
       public       postgres    false    200    2711    201            �
           2606    19524 7   transaction_category transaction_category_id_users_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.transaction_category
    ADD CONSTRAINT transaction_category_id_users_fkey FOREIGN KEY (id_users) REFERENCES public.users(id) ON DELETE CASCADE;
 a   ALTER TABLE ONLY public.transaction_category DROP CONSTRAINT transaction_category_id_users_fkey;
       public       postgres    false    201    198    2706            �
           2606    19544 (   transaction transaction_id_currency_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_id_currency_fkey FOREIGN KEY (id_currency) REFERENCES public.currency(id) ON DELETE CASCADE;
 R   ALTER TABLE ONLY public.transaction DROP CONSTRAINT transaction_id_currency_fkey;
       public       postgres    false    202    197    2704            �
           2606    19549 4   transaction transaction_id_transaction_category_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_id_transaction_category_fkey FOREIGN KEY (id_transaction_category) REFERENCES public.transaction_category(id) ON DELETE CASCADE;
 ^   ALTER TABLE ONLY public.transaction DROP CONSTRAINT transaction_id_transaction_category_fkey;
       public       postgres    false    201    202    2713            �
           2606    19539 %   transaction transaction_id_users_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_id_users_fkey FOREIGN KEY (id_users) REFERENCES public.users(id) ON DELETE CASCADE;
 O   ALTER TABLE ONLY public.transaction DROP CONSTRAINT transaction_id_users_fkey;
       public       postgres    false    198    202    2706            �
           2606    19502 #   user_roles user_roles_id_users_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_id_users_fkey FOREIGN KEY (id_users) REFERENCES public.users(id) ON DELETE CASCADE;
 M   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT user_roles_id_users_fkey;
       public       postgres    false    199    2706    198            �
           2606    19564 .   users_currency users_currency_id_currency_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.users_currency
    ADD CONSTRAINT users_currency_id_currency_fkey FOREIGN KEY (id_currency) REFERENCES public.currency(id) ON DELETE CASCADE;
 X   ALTER TABLE ONLY public.users_currency DROP CONSTRAINT users_currency_id_currency_fkey;
       public       postgres    false    2704    203    197            �
           2606    19559 +   users_currency users_currency_id_users_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.users_currency
    ADD CONSTRAINT users_currency_id_users_fkey FOREIGN KEY (id_users) REFERENCES public.users(id) ON DELETE CASCADE;
 U   ALTER TABLE ONLY public.users_currency DROP CONSTRAINT users_currency_id_users_fkey;
       public       postgres    false    203    198    2706            �
           2606    19491    users users_id_currency_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_id_currency_fkey FOREIGN KEY (id_currency) REFERENCES public.currency(id) ON DELETE CASCADE;
 F   ALTER TABLE ONLY public.users DROP CONSTRAINT users_id_currency_fkey;
       public       postgres    false    197    2704    198            !   +   x�34 ��`.Cː�54�4���0�9�B��b���� �O	*      &   d  x���MN�0���)|�F3���9�V��u�J�Bb�;@�Vi)�^a|#�qR�Y��Ď����g# �v�<���$� k!�\ws�h6�������S�?�Rŏ�,W��`�hC2�BF�Z�;-�)/���TLL�zvP_��%8(W�+�QX�į(����n.eQ�=�]�=�z�W*�kH��J�J`8����x��Y�V:���Q?�����4�A�)Tqv��.N��kQGtG�c�r��5�V�����+{^�%l�g����64�75��^im/=�lU�J��%v\�/y�4����&���V3Q�	V&�ф6�����3�+��9���:�쯇��� ����M>Y      %   �   x�����0���L�ll�e��B� Z�?	Vxވ#NcpDq��}�GI)uN8����M8R�I�\|������gtiBe���E����������䂰��5^�w���Z*��GNqh���:���^��&l���;�@�(�D�`Kر>�9��c�-˩�P�6      $   '   x�34 3NO?g_W.CϜ�5"��/�5�+F��� ��D      #   *   x�34 ��`� .C۔������12CH�A�1z\\\ -�
�      "   �   x�����0D��+���ܴO��

RK�Ǫ 1aYg[��3aU¶^��J^6?�@РX'�*%�U�����.L��v]+�˿����g��wx�	�Q[doV�-b��ȟ�c"��
2~�S�1� ��9�      '   3   x�34 NCe��L��!<�J#0����3�L���`<�=... ���     