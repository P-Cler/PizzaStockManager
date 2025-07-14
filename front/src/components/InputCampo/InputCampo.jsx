import React from 'react';
import styles from './InputCampo.module.css';

export default function InputCampo({ label, name, type = "text", placeholder, value, onChange }) {
  return (
    <div className={styles.inputGroup}>
      <label htmlFor={name}>{label}</label>
      <input
        type={type}
        id={name}
        name={name}
        placeholder={placeholder}
        value={value}
        onChange={onChange}
      />
    </div>
  );
}