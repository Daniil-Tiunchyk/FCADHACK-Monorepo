import React, { useEffect, useState } from "react";

import styles from "./Table.module.css";

import TableFooter from "./TableFooter";

const Table = ({ data }) => {
  const [currentItems, setCurrentItems] = useState([]);
  const [pageCount, setPageCount] = useState(0);
  const [itemOffset, setItemOffset] = useState(0);

  const itemsPerPage = 5; // Количество элементов на страницу

  useEffect(() => {
    // Вычисляем конец и начало выборки данных для текущей страницы
    const endOffset = itemOffset + itemsPerPage;
    setCurrentItems(data.slice(itemOffset, endOffset));
    setPageCount(Math.ceil(data.length / itemsPerPage));
  }, [itemOffset, itemsPerPage, data]);

  // Обработчик изменения страницы
  const handlePageClick = (event) => {
    const newOffset = (event.selected * itemsPerPage) % data.length;
    setItemOffset(newOffset);
  };



    return (
        <>
        <div className="table_block">
          <table className={styles.table}>
       <thead className={styles.tableRowHeader}>
          <tr>
            <th className={styles.tableHeader}>Электронная почта</th>
            <th className={styles.tableHeader}>Точка доступа</th>
            <th className={styles.tableHeader}>Логин</th>
            <th className={styles.tableHeader}> Сообщение</th>
            <th className={styles.tableHeader}> Уровень поддержки</th>
            <th className={styles.tableHeader}> Метка времени</th>
            <th className={styles.tableHeader}> ID пользователя</th>
          </tr>
        </thead>
        <tbody>
          {currentItems.map((el, i) => (
            <tr className={styles.tableRowItems} key={el.i}> {/* ПОМЕНЯТЬ НА user_id */}
              <td className={styles.tableCell}>{el.email}</td>
              <td className={styles.tableCell}>{el.AcPoint}</td>
              <td className={styles.tableCell}>{el.login}</td>
              <td className={styles.tableCell}>{el.message}</td>
              <td className={styles.tableCell}>{el.supLevel}</td>
              <td className={styles.tableCell}>{el.time}</td>
              <td className={styles.tableCell}>{el.user_id}</td>
            </tr>
          ))}
        </tbody>
      </table>
      </div>
      
      <TableFooter pageCount={pageCount} handlePageClick={handlePageClick} />
    </>
    )
};

export default Table;