CREATE SEQUENCE IF NOT EXISTS order_sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS USER_ORDER (
    id BIGINT PRIMARY KEY DEFAULT nextval('order_sequence'::regclass),
    user_id BIGINT NOT NULL,
    create_date TIMESTAMP NOT NULL,
    payment_method VARCHAR NOT NULL,
    shipping_address VARCHAR NOT NULL,
    bill_address VARCHAR NOT NULL,
    payment_status VARCHAR NOT NULL
);


CREATE SEQUENCE IF NOT EXISTS order_product_sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS ORDER_PRODUCT_MAP (
    id BIGINT PRIMARY KEY DEFAULT nextval('order_product_sequence'::regclass),
    product_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES USER_ORDER (id) ON DELETE CASCADE
);


CREATE SEQUENCE IF NOT EXISTS payment_intent_sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS PAYMENT_INTENT (
    id BIGINT PRIMARY KEY DEFAULT nextval('payment_intent_sequence'::regclass),
    uuid VARCHAR NOT NULL,
    payment_intent_id VARCHAR NOT NULL,
    order_id BIGINT NOT NULL,
    CONSTRAINT order_id_fkey FOREIGN KEY (order_id) REFERENCES USER_ORDER (id)
);





