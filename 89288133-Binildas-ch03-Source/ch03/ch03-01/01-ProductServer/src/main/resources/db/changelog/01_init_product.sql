CREATE TABLE IF NOT EXISTS product (
    productid SERIAL PRIMARY KEY,
    prodname TEXT,
    code TEXT,
    title TEXT,
    price REAL,
    category TEXT
);
CREATE TABLE IF NOT EXISTS productcategory (
    categoryid SERIAL PRIMARY KEY,
    name TEXT,
    title TEXT,
    description TEXT,
    imgUrl TEXT
);
