import React from 'react'
import './AuthInput.css'

const AuthInput = ({...props}) => {
  return (
    <div className='authInput_block'>
        <span>{props.placeholder}</span>
        <input {...props} placeholder=""/>
    </div>
  )
}

export default AuthInput