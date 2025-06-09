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
          <li><Link to="/add-promotion">新增優惠</Link></li> {/* 確保新增優惠的連結存在 */}
        </ul>
      </nav>
    </div>
  );
}

export default Home;