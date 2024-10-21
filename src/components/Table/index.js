import React, { useEffect, useState } from "react";

import styles from "./Table.module.css";

import TableFooter from "./TableFooter";
import TableItemModal from "./TableItemModal/TableItemModal";


const Table = ({ data }) => {
  const [isOpenModal, setIsOpenModal] = useState(false)
  const [selectedItem, setSelectedItem] = useState({
    email:"",
    endpoint:"",
    login:"",
    name: "",
    supportLevel:"",
    timestamp: null,
    userID: null,
    gender:"",
    age: null,
  })

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

const formatDate = (timestamp) => {
  return new Intl.DateTimeFormat('ru', {year: 'numeric', month: '2-digit',day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit'}).format(timestamp)
}

  const onClickTableElem = (el) => {
    document.body.style.overflow = 'hidden';
    setSelectedItem(el)
    setIsOpenModal(true)
  }

    return (
        <>
        <div className="table_block">
          <table className={styles.table}>
       <thead className={styles.tableRowHeader}>
          <tr>
            <th className={styles.tableHeader}>Электронная почта</th>
            <th className={styles.tableHeader}>Имя</th>
            <th className={styles.tableHeader}>Точка доступа</th>
            <th className={styles.tableHeader}>Логин</th>
            <th className={styles.tableHeader}>Уровень поддержки</th>
            <th className={styles.tableHeader}>Метка времени</th>
            <th className={styles.tableHeader}>Пол</th>
            <th className={styles.tableHeader}>Возраст</th>
            <th className={styles.tableHeader}>ID пользователя</th>
          </tr>
        </thead>
        <tbody>
          {currentItems.map((el, i) => (
            <tr onClick={() => onClickTableElem(el)} className={styles.tableRowItems} key={el.i}> {/* ПОМЕНЯТЬ НА userID */}
              <td className={styles.tableCell}>{el?.email || '-'}</td>
              <td className={styles.tableCell}>{el?.name || '-'}</td>
              <td className={styles.tableCell}>{el?.endpoint || '-'}</td>
              <td className={styles.tableCell}>{el?.login || '-'}</td>
              <td className={styles.tableCell}>{el?.supportLevel || '-'}</td>
              <td className={styles.tableCell}>{((el?.timestamp && formatDate(el?.timestamp)) || '-')}</td>
              <td className={styles.tableCell}>{el?.gender || '-'}</td>
              <td className={styles.tableCell}>{el?.age || '-'}</td>
              <td className={styles.tableCell}>{el?.userID || '-'}</td>
            </tr>
          ))}
        </tbody>
      </table>
      </div>
      {isOpenModal && <TableItemModal formatDate={formatDate} closeModal={setIsOpenModal} selectedItem={selectedItem} />}
      <TableFooter pageCount={pageCount} handlePageClick={handlePageClick} />
    </>
    )
};

export default Table;