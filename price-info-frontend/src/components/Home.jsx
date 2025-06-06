import React from 'react';
import { Link } from 'react-router-dom';

function Home() {
  return (
    <div>
      <h2>歡迎使用價格管理系統</h2>
      <nav>
        <ul>
          <li><Link to="/price-list">價格列表</Link></li>
          <li><Link to="/csv-upload">CSV 匯入</Link></li>
          <li><Link to="/search">價格搜尋</Link></li>
          <li><Link to="/add-price">新增價格</Link></li>
        </ul>
      </nav>
    </div>
  );
}

export default Home;