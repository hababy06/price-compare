import React, { useState } from "react";
import axios from "axios";

function CsvUpload() {
  const [file, setFile] = useState(null);
  const [msg, setMsg] = useState("");

  const handleChange = (e) => {
    setFile(e.target.files[0]);
    setMsg("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!file) {
      setMsg("請先選擇檔案！");
      return;
    }
    const formData = new FormData();
    formData.append("file", file);

    try {
      const res = await axios.post("/api/import/price-csv", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      setMsg(res.data);
    } catch (err) {
      setMsg(
        err.response?.data ? err.response.data : "上傳失敗，請檢查格式或伺服器"
      );
    }
  };

  return (
    <div style={{ maxWidth: 400, margin: "2rem auto" }}>
      <form onSubmit={handleSubmit}>
        <label>
          選擇CSV檔案：
          <input type="file" accept=".csv" onChange={handleChange} />
        </label>
        <br />
        <button type="submit" style={{ marginTop: 12 }}>上傳</button>
      </form>
      {msg && <div style={{ marginTop: 16 }}>{msg}</div>}
    </div>
  );
}

export default CsvUpload;