import React, { useState } from 'react';
import axios from 'axios';

function SearchPriceInfo() {
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(false);
  const [results, setResults] = useState([]);

  const handleSearch = async () => {
    setLoading(true);
    try {
      const response = await axios.get(`/api/price-infos/search`, {
        params: { name: searchTerm },
      });
      const sortedResults = response.data.sort((a, b) => a.price - b.price);
      setResults(sortedResults);
    } catch (error) {
      console.error('Error fetching price info:', error);
      setResults([]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h2>商品價格搜尋</h2>
      <input
        type="text"
        placeholder="輸入商品名稱"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />
      <button onClick={handleSearch}>搜尋</button>
      {loading && <p>載入中...</p>}
      {results.length > 0 && (
        <table border="1">
          <thead>
            <tr>
              <th>商品名稱</th>
              <th>商店名稱</th>
              <th>價格</th>
            </tr>
          </thead>
          <tbody>
            {results.map((item, index) => (
              <tr key={index}>
                <td>{item.productName}</td>
                <td>{item.storeName}</td>
                <td>{item.price}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
      {results.length === 0 && !loading && <p>沒有找到相關商品。</p>}
    </div>
  );
}

export default SearchPriceInfo;