import axios from 'axios';
import { useEffect, useState } from 'react';

function PriceInfoList() {
  const [data, setData] = useState([]);
  const [form, setForm] = useState({
    productId: '',
    storeId: '',
    price: '',
    dateTime: ''
  });

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = () => {
    axios.get('http://localhost:8080/api/price-infos')
      .then(res => setData(res.data))
      .catch(err => console.error(err));
  };

  const handleChange = e => {
    setForm({...form, [e.target.name]: e.target.value});
  };

  const handleSubmit = e => {
    e.preventDefault();
    axios.post('http://localhost:8080/api/price-infos', form)
      .then(res => {
        fetchData(); // 新增成功後刷新列表
        setForm({productId:'', storeId:'', price:'', dateTime:''});
      })
      .catch(err => alert('新增失敗！請檢查欄位與後端設定'));
  };

  return (
    <div>
      <h2>新增價格資訊</h2>
      <form onSubmit={handleSubmit} style={{marginBottom: '2rem'}}>
        <input
          name="productId"
          placeholder="產品ID"
          value={form.productId}
          onChange={handleChange}
          required
        />
        <input
          name="storeId"
          placeholder="商店ID"
          value={form.storeId}
          onChange={handleChange}
          required
        />
        <input
          name="price"
          type="number"
          step="0.01"
          placeholder="價格"
          value={form.price}
          onChange={handleChange}
          required
        />
        <input
          name="dateTime"
          type="datetime-local"
          placeholder="時間"
          value={form.dateTime}
          onChange={handleChange}
          required
        />
        <button type="submit">新增</button>
      </form>
      <h2>價格資訊列表</h2>
      <ul>
        {data.length === 0 && <li>目前沒有資料</li>}
        {data.map(item => (
          <li key={item.id}>
            ID: {item.id}，產品ID: {item.productId}，商店ID: {item.storeId}，價格: {item.price}，時間: {item.dateTime}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default PriceInfoList;