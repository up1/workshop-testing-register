import React, { useState } from 'react';
import axios from 'axios';

const RegisterForm = () => {
  const [formData, setFormData] = useState({
    name: '',
    age: '',
    phone: '',
    email: '',
    password: '',
    address: '',
  });

  const [errors, setErrors] = useState([]);
  const [successMessage, setSuccessMessage] = useState('');

  const isThaiText = (text) => /^[\u0E00-\u0E7F\s]+$/.test(text);
  const isThaiMobile = (phone) => /^0[89]\d{8}$/.test(phone);
  const isNumeric = (text) => /^\d+$/.test(text);
  const isEmail = (email) =>
    /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);

  const validate = () => {
    const e = [];
    const { name, age, phone, email, password, address } = formData;

    if (!name) e.push('กรุณากรอกชื่อ');
    else if (!isThaiText(name)) e.push('กรุณากรอกภาษาไทยเท่านั้น'); // TC002

    if (age === '') e.push('กรุณากรอกอายุ');
    else if (parseInt(age) > 60) e.push('อายุไม่เกิน 60 ปี'); // TC001

    if (!phone) e.push('กรุณากรอกเบอร์โทร');
    else if (!isNumeric(phone)) e.push('กรุณากรอกตัวเลขเท่านั้น'); // TC003
    else if (!isThaiMobile(phone)) e.push('เบอร์โทรไม่ถูกต้อง ต้องขึ้นต้นด้วย 08, 09'); // TC019

    if (!email) e.push('กรุณากรอกอีเมล');
    else if (!isEmail(email)) e.push('อีเมลไม่ถูกต้อง'); // TC017

    if (!password) e.push('กรุณากรอกรหัสผ่าน');
    else if (password.length < 8) e.push('รหัสผ่านต้องมีอย่างน้อย 8 ตัวอักษร'); // TC018

    if (!address) e.push('กรุณากรอกที่อยู่'); // TC020

    return e;
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
    setErrors([]);
    setSuccessMessage('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const validationErrors = validate();
    if (validationErrors.length > 0) {
      setErrors(validationErrors); // TC021
      return;
    }

    try {
      // const response = await axios.post('http://localhost:3000/api/register', formData);
      const response = await axios.post('/api/register', formData);
      setSuccessMessage(response.data.message);
      setErrors([]);
      setFormData({
        name: '',
        age: '',
        phone: '',
        email: '',
        password: '',
        address: '',
      });
    } catch (err) {
      if (err.response && err.response.data.errors) {
        setErrors(err.response.data.errors);
      }
    }
  };

  return (
    <div style={{ maxWidth: 500, margin: '0 auto' }}>
      <h2>ฟอร์มสมัครสมาชิก</h2>
      {errors.length > 0 && (
        <div style={{ color: 'red' }}>
          {errors.map((e, i) => (
            <p key={i}>{e}</p>
          ))}
        </div>
      )}
      {successMessage && <div style={{ color: 'green' }}>{successMessage}</div>}
      <form onSubmit={handleSubmit}>
        <div>
          <label>ชื่อ (ภาษาไทย)</label>
          <input type="text" name="name" value={formData.name} onChange={handleChange} />
        </div>
        <div>
          <label>อายุ</label>
          <input type="number" name="age" value={formData.age} onChange={handleChange} />
        </div>
        <div>
          <label>เบอร์โทร</label>
          <input type="text" name="phone" value={formData.phone} onChange={handleChange} />
        </div>
        <div>
          <label>อีเมล</label>
          <input type="text" name="email" value={formData.email} onChange={handleChange} />
        </div>
        <div>
          <label>รหัสผ่าน</label>
          <input type="password" name="password" value={formData.password} onChange={handleChange} />
        </div>
        <div>
          <label>ที่อยู่</label>
          <textarea name="address" value={formData.address} onChange={handleChange}></textarea>
        </div>
        <button type="submit">สมัครสมาชิก</button>
      </form>
    </div>
  );
};

export default RegisterForm;
