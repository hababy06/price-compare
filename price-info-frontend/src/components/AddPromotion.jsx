import React, { useState } from 'react';
import axios from 'axios';

function AddPromotion() {
  const [formData, setFormData] = useState({
    storeId: '', // 配合後端的 Store 關聯
    productId: '', // 配合後端的 Product 關聯
    description: '',
    discountType: '',
    discountValue: '',
    startTime: '',
    endTime: '',
  });
  const [errors, setErrors] = useState({});
  const [successMessage, setSuccessMessage] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const validateForm = () => {
    const newErrors = {};
    if (!formData.storeId) newErrors.storeId = '商家是必填的';
    if (!formData.productId) newErrors.productId = '商品是必填的';
    if (!formData.description) newErrors.description = '優惠內容是必填的';
    if (!formData.discountType) newErrors.discountType = '優惠類型是必填的';
    if (!formData.discountValue) newErrors.discountValue = '優惠值是必填的';
    if (!formData.startTime) newErrors.startTime = '起始時間是必填的';
    if (!formData.endTime) newErrors.endTime = '結束時間是必填的';
    if (formData.startTime && formData.endTime && formData.startTime > formData.endTime) {
      newErrors.dateRange = '起始時間不能晚於結束時間';
    }
    return newErrors;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const validationErrors = validateForm();
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }
    try {
      await axios.post('/api/promotions', formData);
      setSuccessMessage('優惠已成功新增！');
      setFormData({
        storeId: '',
        productId: '',
        description: '',
        discountType: '',
        discountValue: '',
        startTime: '',
        endTime: '',
      });
      setErrors({});
    } catch (error) {
      console.error('新增優惠失敗:', error);
      setSuccessMessage('');
    }
  };

  return (
    <div>
      <h2>新增優惠</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>
            商家:
            <input
              type="text"
              name="storeId"
              value={formData.storeId}
              onChange={handleChange}
            />
          </label>
          {errors.storeId && <p style={{ color: 'red' }}>{errors.storeId}</p>}
        </div>
        <div>
          <label>
            商品:
            <input
              type="text"
              name="productId"
              value={formData.productId}
              onChange={handleChange}
            />
          </label>
          {errors.productId && <p style={{ color: 'red' }}>{errors.productId}</p>}
        </div>
        <div>
          <label>
            優惠內容:
            <textarea
              name="description"
              value={formData.description}
              onChange={handleChange}
            />
          </label>
          {errors.description && <p style={{ color: 'red' }}>{errors.description}</p>}
        </div>
        <div>
          <label>
            優惠類型:
            <input
              type="text"
              name="discountType"
              value={formData.discountType}
              onChange={handleChange}
            />
          </label>
          {errors.discountType && <p style={{ color: 'red' }}>{errors.discountType}</p>}
        </div>
        <div>
          <label>
            優惠值:
            <input
              type="number"
              name="discountValue"
              value={formData.discountValue}
              onChange={handleChange}
            />
          </label>
          {errors.discountValue && <p style={{ color: 'red' }}>{errors.discountValue}</p>}
        </div>
        <div>
          <label>
            起始時間:
            <input
              type="datetime-local"
              name="startTime"
              value={formData.startTime}
              onChange={handleChange}
            />
          </label>
          {errors.startTime && <p style={{ color: 'red' }}>{errors.startTime}</p>}
        </div>
        <div>
          <label>
            結束時間:
            <input
              type="datetime-local"
              name="endTime"
              value={formData.endTime}
              onChange={handleChange}
            />
          </label>
          {errors.endTime && <p style={{ color: 'red' }}>{errors.endTime}</p>}
        </div>
        {errors.dateRange && <p style={{ color: 'red' }}>{errors.dateRange}</p>}
        <button type="submit">送出</button>
      </form>
      {successMessage && <p style={{ color: 'green' }}>{successMessage}</p>}
    </div>
  );
}

export default AddPromotion;