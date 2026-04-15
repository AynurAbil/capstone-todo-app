import { useState } from "react";

type TodoFormProps = {
  onCreate: (todo: {
    title: string;
    status: string;
    priority: string;
    dueDate: string;
  }) => Promise<void>;
};

function TodoForm({ onCreate }: TodoFormProps) {
  const [title, setTitle] = useState("");
  const [status, setStatus] = useState("");
  const [priority, setPriority] = useState("");
  const [dueDate, setDueDate] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!title || !status || !priority || !dueDate) {
        alert("Please fill all fields");
        return;
      }

    await onCreate({
      title,
      status,
      priority,
      dueDate,
    });

    setTitle("");
    setStatus("");
    setPriority("");
    setDueDate("");
  };

  return (
    <form
      onSubmit={handleSubmit}
      style={{
        display: "flex",
        flexDirection: "column",
        gap: "1rem",
        marginBottom: "2rem",
        padding: "1rem",
        border: "1px solid #ccc",
        borderRadius: "8px",
      }}
    >
      <input
        type="text"
        placeholder="Todo title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />

      <select value={status} onChange={(e) => setStatus(e.target.value)}>
        <option value="" disabled>
            Select status
        </option>
        <option value="OPEN">OPEN</option>
        <option value="IN_PROGRESS">IN_PROGRESS</option>
        <option value="DONE">DONE</option>
      </select>

      <select value={priority} onChange={(e) => setPriority(e.target.value)}>
        <option value="" disabled>
            Select priority
        </option>
        <option value="LOW">LOW</option>
        <option value="MEDIUM">MEDIUM</option>
        <option value="HIGH">HIGH</option>
      </select>

      <input
        type="date"
        aria-label="Due date"
        value={dueDate}
        onChange={(e) => setDueDate(e.target.value)}
      />

      <button type="submit">Add Todo</button>
    </form>
  );
}

export default TodoForm;
