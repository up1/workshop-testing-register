const validator = require('validator');

const isThai = (text) => /^[\u0E00-\u0E7F\s]+$/.test(text);
const isThaiMobile = (phone) => /^0[89]\d{8}$/.test(phone);

exports.register = (req, res) => {
  // check json body
  if (!req.body) {
    return res.status(400).json({ error: 'กรุณากรอกข้อมูล' });
  }
  const { name, age, phone, email, password, address } = req.body;
  const errors = [];

  if (!name) {
    errors.push('กรุณากรอกชื่อ');
  } else if (!isThai(name)) {
    errors.push('กรุณากรอกภาษาไทยเท่านั้น');
  }

  if (!age && age !== 0) {
    errors.push('กรุณากรอกอายุ');
  } else if (parseInt(age) > 60) {
    errors.push('อายุไม่เกิน 60 ปี');
  }

  if (!phone) {
    errors.push('กรุณากรอกเบอร์โทร');
  } else if (!/^\d+$/.test(phone)) {
    errors.push('กรุณากรอกตัวเลขเท่านั้น');
  } else if (!isThaiMobile(phone)) {
    errors.push('เบอร์โทรไม่ถูกต้อง ต้องขึ้นต้นด้วย 08, 09');
  }

  if (!email) {
    errors.push('กรุณากรอกอีเมล');
  } else if (!validator.isEmail(email)) {
    errors.push('อีเมลไม่ถูกต้อง');
  }

  if (!password) {
    errors.push('กรุณากรอกรหัสผ่าน');
  } else if (password.length < 8) {
    errors.push('รหัสผ่านต้องมีอย่างน้อย 8 ตัวอักษร');
  }

  if (!address) {
    errors.push('กรุณากรอกที่อยู่');
  }

  if (errors.length > 0) {
    return res.status(400).json({ errors });
  }

  // บันทึกลง MySQL
  const db = require('../db');
  db.query('INSERT INTO users SET ?', { name, age, phone, email, password, address })
    .then(() => res.status(201).json({ message: 'สมัครสมาชิกสำเร็จ' }))
    .catch(err => res.status(500).json({ error: err.message }));

};
