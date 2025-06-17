--
-- PostgreSQL database cluster dump
--

-- Started on 2025-06-14 17:26:13

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

CREATE ROLE bus_admin WITH
  LOGIN
  PASSWORD 'passwd'
  NOSUPERUSER
  INHERIT
  NOCREATEROLE
  CREATEDB
  NOREPLICATION
  BYPASSRLS;


--
-- Databases
--

--
-- Database "template1" dump
--

\connect template1

--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.4

-- Started on 2025-06-14 17:26:15

SET statement_timeout = 0;
--SET lock_timeout = 0;
--SET idle_in_transaction_session_timeout = 0;
--SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

-- Completed on 2025-06-14 17:26:24

--
-- PostgreSQL database dump complete
--

--
-- Database "busDB" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.4

-- Started on 2025-06-14 17:26:25

SET statement_timeout = 0;
--SET lock_timeout = 0;
--SET idle_in_transaction_session_timeout = 0;
--SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3405 (class 1262 OID 16389)
-- Name: busDB; Type: DATABASE; Schema: -; Owner: bus_user
--

CREATE DATABASE busDB WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'C' LC_CTYPE = 'C';


ALTER DATABASE busDB OWNER TO bus_user;

\connect busDB

SET statement_timeout = 0;
--SET lock_timeout = 0;
--SET idle_in_transaction_session_timeout = 0;
--SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 57344)
-- Name: buses; Type: TABLE; Schema: public; Owner: bus_user
--

CREATE TABLE public.buses (
    matricula character varying(10) NOT NULL,
    modelo character varying(50),
    capacidad integer
);


ALTER TABLE public.buses OWNER TO bus_user;

--
-- TOC entry 221 (class 1259 OID 57391)
-- Name: pasajeros; Type: TABLE; Schema: public; Owner: bus_user
--

CREATE TABLE public.pasajeros (
    pasajero_id integer NOT NULL,
    nombre character varying(100),
    correo character varying(50)
);


ALTER TABLE public.pasajeros OWNER TO bus_user;

--
-- TOC entry 223 (class 1259 OID 73728)
-- Name: pasajeros_pasajero_id_seq; Type: SEQUENCE; Schema: public; Owner: bus_user
--

ALTER TABLE public.pasajeros ALTER COLUMN pasajero_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pasajeros_pasajero_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 222 (class 1259 OID 57398)
-- Name: pasajes; Type: TABLE; Schema: public; Owner: bus_user
--

CREATE TABLE public.pasajes (
    pasaje_id integer NOT NULL,
    viaje_id integer,
    pasajero_id integer,
    punto_origen_id integer,
    punto_destino_id integer,
    precio numeric(8,2),
    fecha_compra timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    asiento integer
);


ALTER TABLE public.pasajes OWNER TO bus_user;

--
-- TOC entry 224 (class 1259 OID 73729)
-- Name: pasajes_pasaje_id_seq; Type: SEQUENCE; Schema: public; Owner: bus_user
--

ALTER TABLE public.pasajes ALTER COLUMN pasaje_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pasajes_pasaje_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 219 (class 1259 OID 57356)
-- Name: puntosintermedios; Type: TABLE; Schema: public; Owner: bus_user
--

CREATE TABLE public.puntosintermedios (
    punto_id integer NOT NULL,
    ruta_id integer,
    nombre_punto character varying(100),
    orden integer,
    lat numeric,
    long numeric
);


ALTER TABLE public.puntosintermedios OWNER TO bus_user;

--
-- TOC entry 225 (class 1259 OID 73730)
-- Name: puntosintermedios_punto_id_seq; Type: SEQUENCE; Schema: public; Owner: bus_user
--

ALTER TABLE public.puntosintermedios ALTER COLUMN punto_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.puntosintermedios_punto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 228 (class 1259 OID 106496)
-- Name: puntosintermedios_viaje; Type: TABLE; Schema: public; Owner: bus_user
--

CREATE TABLE public.puntosintermedios_viaje (
    viaje_id integer NOT NULL,
    punto_id integer NOT NULL,
    hora_llegada timestamp without time zone,
    hora_salida timestamp without time zone,
    hora_llegada_estimada time without time zone
);


ALTER TABLE public.puntosintermedios_viaje OWNER TO bus_user;

--
-- TOC entry 218 (class 1259 OID 57351)
-- Name: rutas; Type: TABLE; Schema: public; Owner: bus_user
--

CREATE TABLE public.rutas (
    ruta_id integer NOT NULL,
    origen character varying(100),
    destino character varying(100),
    duracion_estimada time without time zone
);


ALTER TABLE public.rutas OWNER TO bus_user;

--
-- TOC entry 226 (class 1259 OID 73731)
-- Name: rutas_ruta_id_seq; Type: SEQUENCE; Schema: public; Owner: bus_user
--

ALTER TABLE public.rutas ALTER COLUMN ruta_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.rutas_ruta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 220 (class 1259 OID 57366)
-- Name: viajes; Type: TABLE; Schema: public; Owner: bus_user
--

CREATE TABLE public.viajes (
    viaje_id integer NOT NULL,
    ruta_id integer,
    matricula character varying(10),
    fecha date,
    hora_salida time without time zone,
    hora_salida_estimada time without time zone
);


ALTER TABLE public.viajes OWNER TO bus_user;

--
-- TOC entry 227 (class 1259 OID 73732)
-- Name: viajes_viaje_id_seq; Type: SEQUENCE; Schema: public; Owner: bus_user
--

ALTER TABLE public.viajes ALTER COLUMN viaje_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.viajes_viaje_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 3388 (class 0 OID 57344)
-- Dependencies: 217
-- Data for Name: buses; Type: TABLE DATA; Schema: public; Owner: bus_user
--

COPY public.buses (matricula, modelo, capacidad) FROM stdin;
ABC123	Mercedes Sprinter	30
XYZ789	Volvo 9800	45
BB2	Bus largo	15
\.


--
-- TOC entry 3392 (class 0 OID 57391)
-- Dependencies: 221
-- Data for Name: pasajeros; Type: TABLE DATA; Schema: public; Owner: bus_user
--

COPY public.pasajeros (pasajero_id, nombre, correo) FROM stdin;
1	Ana Torres	12345678
2	Luis Pérez	87654321
3	Marta Ruiz	11223344
4	Carlos Díaz	55667788
7	vicente rosales	vicente.rosales.m@mail.pucv.cl
8	Manuel Aguilera	manuel.aguilera.e@mail.pucv.cl
9	Alex alfaro	alex.alfaro.s@mail.pucv.cl
\.


--
-- TOC entry 3393 (class 0 OID 57398)
-- Dependencies: 222
-- Data for Name: pasajes; Type: TABLE DATA; Schema: public; Owner: bus_user
--

COPY public.pasajes (pasaje_id, viaje_id, pasajero_id, punto_origen_id, punto_destino_id, precio, fecha_compra, asiento) FROM stdin;
2	1	1	1	2	\N	2025-05-18 20:55:46.037734	1
3	1	2	2	4	\N	2025-05-20 01:17:26.745109	1
4	1	3	3	4	\N	2025-05-20 01:29:13.626668	\N
5	3	4	8	9	\N	2025-05-20 03:10:09.617599	\N
\.


--
-- TOC entry 3390 (class 0 OID 57356)
-- Dependencies: 219
-- Data for Name: puntosintermedios; Type: TABLE DATA; Schema: public; Owner: bus_user
--

COPY public.puntosintermedios (punto_id, ruta_id, nombre_punto, orden, lat, long) FROM stdin;
1	1	Salamanca	1	-31.778873280943287	-70.9631148049397
5	2	Rancagua	2	-34.211609086106854	-70.78237508483653
2	1	Illapel	2	-31.638271830470714	-71.17653796758567
8	3	Quilpue	1	-33.04571894737512	-71.43953349014032
9	3	Viña del mar	2	-33.025566336116675	-71.54751356876643
10	3	Calera	3	-32.78543528888529	-71.18882671417433
11	3	Los vilos	4	-31.909935261116694	-71.51171983519112
12	3	Coquimbo	5	-29.95241731748097	-71.33646789772341
13	3	La serena	6	-29.910268449587257	-71.24412272218012
14	3	Vallenar	7	-28.573120242814777	-70.76775833075774
15	3	Copiapo	8	-27.37568813297118	-70.32494862120343
6	2	Santiago	3	-33.45340806687254	-70.68666744746241
7	2	Santiago San Borja	1	-33.45446412724266	-70.67985528920076
4	1	Viña del mar	4	-33.025566336116675	-71.54751356876643
3	1	Calera	3	-32.78543528888529	-71.18882671417433
\.


--
-- TOC entry 3399 (class 0 OID 106496)
-- Dependencies: 228
-- Data for Name: puntosintermedios_viaje; Type: TABLE DATA; Schema: public; Owner: bus_user
--

COPY public.puntosintermedios_viaje (viaje_id, punto_id, hora_llegada, hora_salida, hora_llegada_estimada) FROM stdin;
3	1	\N	\N	\N
1	1	\N	\N	\N
3	3	\N	\N	\N
1	3	\N	\N	\N
3	4	\N	\N	\N
1	4	\N	\N	\N
2	5	\N	\N	\N
3	2	\N	\N	\N
1	2	\N	\N	\N
2	6	\N	\N	\N
2	7	\N	\N	\N
\.


--
-- TOC entry 3389 (class 0 OID 57351)
-- Dependencies: 218
-- Data for Name: rutas; Type: TABLE DATA; Schema: public; Owner: bus_user
--

COPY public.rutas (ruta_id, origen, destino, duracion_estimada) FROM stdin;
1	Salamanca	Viña del mar	03:30:00
2	Rancagua	Viña del mar	04:15:00
3	Quilpue	Copiapo	\N
\.


--
-- TOC entry 3391 (class 0 OID 57366)
-- Dependencies: 220
-- Data for Name: viajes; Type: TABLE DATA; Schema: public; Owner: bus_user
--

COPY public.viajes (viaje_id, ruta_id, matricula, fecha, hora_salida, hora_salida_estimada) FROM stdin;
1	1	ABC123	2025-05-15	08:00:00	\N
2	2	XYZ789	2025-05-16	09:30:00	\N
3	3	XYZ789	2025-05-20	10:30:00	\N
\.


--
-- TOC entry 3407 (class 0 OID 0)
-- Dependencies: 223
-- Name: pasajeros_pasajero_id_seq; Type: SEQUENCE SET; Schema: public; Owner: bus_user
--

SELECT pg_catalog.setval('public.pasajeros_pasajero_id_seq', 17, true);


--
-- TOC entry 3408 (class 0 OID 0)
-- Dependencies: 224
-- Name: pasajes_pasaje_id_seq; Type: SEQUENCE SET; Schema: public; Owner: bus_user
--

SELECT pg_catalog.setval('public.pasajes_pasaje_id_seq', 3, true);


--
-- TOC entry 3409 (class 0 OID 0)
-- Dependencies: 225
-- Name: puntosintermedios_punto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: bus_user
--

SELECT pg_catalog.setval('public.puntosintermedios_punto_id_seq', 15, true);


--
-- TOC entry 3410 (class 0 OID 0)
-- Dependencies: 226
-- Name: rutas_ruta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: bus_user
--

SELECT pg_catalog.setval('public.rutas_ruta_id_seq', 4, false);


--
-- TOC entry 3411 (class 0 OID 0)
-- Dependencies: 227
-- Name: viajes_viaje_id_seq; Type: SEQUENCE SET; Schema: public; Owner: bus_user
--

SELECT pg_catalog.setval('public.viajes_viaje_id_seq', 3, true);


--
-- TOC entry 3217 (class 2606 OID 57350)
-- Name: buses buses_matricula_key; Type: CONSTRAINT; Schema: public; Owner: bus_user
--

ALTER TABLE ONLY public.buses
    ADD CONSTRAINT buses_matricula_key UNIQUE (matricula);


--
-- TOC entry 3227 (class 2606 OID 114689)
-- Name: pasajeros pasajeros_documento_identidad_key; Type: CONSTRAINT; Schema: public; Owner: bus_user
--

ALTER TABLE ONLY public.pasajeros
    ADD CONSTRAINT pasajeros_documento_identidad_key UNIQUE (correo);


--
-- TOC entry 3229 (class 2606 OID 57395)
-- Name: pasajeros pasajeros_pkey; Type: CONSTRAINT; Schema: public; Owner: bus_user
--

ALTER TABLE ONLY public.pasajeros
    ADD CONSTRAINT pasajeros_pkey PRIMARY KEY (pasajero_id);


--
-- TOC entry 3231 (class 2606 OID 57403)
-- Name: pasajes pasajes_pkey; Type: CONSTRAINT; Schema: public; Owner: bus_user
--

ALTER TABLE ONLY public.pasajes
    ADD CONSTRAINT pasajes_pkey PRIMARY KEY (pasaje_id);


--
-- TOC entry 3219 (class 2606 OID 65537)
-- Name: buses pk_buses; Type: CONSTRAINT; Schema: public; Owner: bus_user
--

ALTER TABLE ONLY public.buses
    ADD CONSTRAINT pk_buses PRIMARY KEY (matricula);


--
-- TOC entry 3223 (class 2606 OID 57360)
-- Name: puntosintermedios puntosintermedios_pkey; Type: CONSTRAINT; Schema: public; Owner: bus_user
--

ALTER TABLE ONLY public.puntosintermedios
    ADD CONSTRAINT puntosintermedios_pkey PRIMARY KEY (punto_id);


--
-- TOC entry 3233 (class 2606 OID 106500)
-- Name: puntosintermedios_viaje puntosintermedios_viaje_pkey; Type: CONSTRAINT; Schema: public; Owner: bus_user
--

ALTER TABLE ONLY public.puntosintermedios_viaje
    ADD CONSTRAINT puntosintermedios_viaje_pkey PRIMARY KEY (viaje_id, punto_id);


--
-- TOC entry 3221 (class 2606 OID 57355)
-- Name: rutas rutas_pkey; Type: CONSTRAINT; Schema: public; Owner: bus_user
--

ALTER TABLE ONLY public.rutas
    ADD CONSTRAINT rutas_pkey PRIMARY KEY (ruta_id);


--
-- TOC entry 3225 (class 2606 OID 57370)
-- Name: viajes viajes_pkey; Type: CONSTRAINT; Schema: public; Owner: bus_user
--

ALTER TABLE ONLY public.viajes
    ADD CONSTRAINT viajes_pkey PRIMARY KEY (viaje_id);


--
-- TOC entry 3241 (class 2606 OID 106506)
-- Name: puntosintermedios_viaje fk_punto; Type: FK CONSTRAINT; Schema: public; Owner: bus_user
--

ALTER TABLE ONLY public.puntosintermedios_viaje
    ADD CONSTRAINT fk_punto FOREIGN KEY (punto_id) REFERENCES public.puntosintermedios(punto_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3242 (class 2606 OID 106501)
-- Name: puntosintermedios_viaje fk_viaje; Type: FK CONSTRAINT; Schema: public; Owner: bus_user
--

ALTER TABLE ONLY public.puntosintermedios_viaje
    ADD CONSTRAINT fk_viaje FOREIGN KEY (viaje_id) REFERENCES public.viajes(viaje_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3237 (class 2606 OID 57409)
-- Name: pasajes pasajes_pasajero_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: bus_user
--

ALTER TABLE ONLY public.pasajes
    ADD CONSTRAINT pasajes_pasajero_id_fkey FOREIGN KEY (pasajero_id) REFERENCES public.pasajeros(pasajero_id);


--
-- TOC entry 3238 (class 2606 OID 57424)
-- Name: pasajes pasajes_punto_destino_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: bus_user
--

ALTER TABLE ONLY public.pasajes
    ADD CONSTRAINT pasajes_punto_destino_id_fkey FOREIGN KEY (punto_destino_id) REFERENCES public.puntosintermedios(punto_id);


--
-- TOC entry 3239 (class 2606 OID 57419)
-- Name: pasajes pasajes_punto_origen_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: bus_user
--

ALTER TABLE ONLY public.pasajes
    ADD CONSTRAINT pasajes_punto_origen_id_fkey FOREIGN KEY (punto_origen_id) REFERENCES public.puntosintermedios(punto_id);


--
-- TOC entry 3240 (class 2606 OID 57404)
-- Name: pasajes pasajes_viaje_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: bus_user
--

ALTER TABLE ONLY public.pasajes
    ADD CONSTRAINT pasajes_viaje_id_fkey FOREIGN KEY (viaje_id) REFERENCES public.viajes(viaje_id);


--
-- TOC entry 3234 (class 2606 OID 57361)
-- Name: puntosintermedios puntosintermedios_ruta_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: bus_user
--

ALTER TABLE ONLY public.puntosintermedios
    ADD CONSTRAINT puntosintermedios_ruta_id_fkey FOREIGN KEY (ruta_id) REFERENCES public.rutas(ruta_id);


--
-- TOC entry 3235 (class 2606 OID 65556)
-- Name: viajes viajes_matricula_fkey; Type: FK CONSTRAINT; Schema: public; Owner: bus_user
--

ALTER TABLE ONLY public.viajes
    ADD CONSTRAINT viajes_matricula_fkey FOREIGN KEY (matricula) REFERENCES public.buses(matricula);


--
-- TOC entry 3236 (class 2606 OID 57371)
-- Name: viajes viajes_ruta_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: bus_user
--

ALTER TABLE ONLY public.viajes
    ADD CONSTRAINT viajes_ruta_id_fkey FOREIGN KEY (ruta_id) REFERENCES public.rutas(ruta_id);


--
-- TOC entry 3406 (class 0 OID 0)
-- Dependencies: 3405
-- Name: DATABASE busDB; Type: ACL; Schema: -; Owner: bus_user
--
-- Completed on 2025-06-14 17:26:50

--
-- PostgreSQL database dump complete
--

-- Completed on 2025-06-14 17:26:50

--
-- PostgreSQL database cluster dump complete
--

