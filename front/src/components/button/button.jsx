import styles from "../button/button.module.css"

export function Button({ type, message, onClick, className = "" }) {
  return (
    <button
      type={type}
      className={`${styles.button} ${className}`}
      onClick={onClick}
    >
      {message}
    </button>
  );
}