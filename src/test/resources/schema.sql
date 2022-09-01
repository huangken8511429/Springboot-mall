CREATE TABLE IF NOT EXISTS product          --記得CREATE TABLE後面要加上IF NOT EXISTS 因為我們單元測試的時候，H2資料庫會運行好幾次，這樣的話資料會一直重複加
(
    product_id         INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    product_name       VARCHAR(128)  NOT NULL,
    category           VARCHAR(32)  NOT NULL,
    image_url          VARCHAR(256) NOT NULL,
    price              INT          NOT NULL,
    stock              INT          NOT NULL,
    description        VARCHAR(1024),
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
    );
