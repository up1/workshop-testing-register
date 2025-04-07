const mysql = require('mysql2');

const pool = mysql.createPool({
  host: 'localhost',
  user: 'user01',
  password: 'password01',
  database: 'demodb',
  waitForConnections: true,
  connectionLimit: 10,
  queueLimit: 0,
  charset: 'utf8mb4',
});

module.exports = pool.promise();
