import React from 'react'
import './Input.css'

const Input = ({...props}) => {
  return (
    <div className='Input_block'>
      <input {...props} />
      <div >
        <img src="./search.svg" alt="search" />
      </div>
    </div>
  )
}

export default Input