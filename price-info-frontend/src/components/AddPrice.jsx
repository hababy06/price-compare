import React from 'react';

function AddPrice() {
  return (
    <div>
      <h2>新增商品價格</h2>
      <form>
        <label>
          商品名稱:
          <input type="text" name="productName" />
        </label>
        <br />
        <label>
          商店名稱:
          <input type="text" name="storeName" />
        </label>
        <br />
        <label>
          價格:
          <input type="number" name="price" />
        </label>
        <br />
        <button type="submit">新增</button>
      </form>
    </div>
  );
}

export default AddPrice;