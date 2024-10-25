import React, { useEffect, useState } from "react";
import styles from "./TableFooter.module.css";
import "./pagination.css"
import ReactPaginate from "react-paginate";

const TableFooter = ({pageCount,handlePageClick}) => {
  const [ screenWidth,setScreenWidth ] = useState(null)
  useEffect(() => {
    return () => {window.removeEventListener('resize', setScreenWidth(window.innerWidth));
  }
  }, [screenWidth])

  useEffect(() => {
    if (pageCount === 1) {
      handlePageClick(0);
    }
  }, [pageCount, handlePageClick]);

  return (
    <div className={styles.tableFooter}>
      <ReactPaginate
        nextLabel=">"
        onPageChange={handlePageClick}
        pageRangeDisplayed={screenWidth <= 768 ? 1 : 3}
        marginPagesDisplayed={screenWidth <= 768 ? 1 : 2}
        pageCount={pageCount}
        previousLabel="<"
        pageClassName="page-item"
        pageLinkClassName="page-link"
        previousClassName="page-item"
        previousLinkClassName="page-link"
        nextClassName="page-item"
        nextLinkClassName="page-link"
        breakLabel="..."
        breakClassName="page-item"
        breakLinkClassName="page-link"
        containerClassName="pagination"
        activeClassName="active"
      />
    </div>
  );

};

export default TableFooter;