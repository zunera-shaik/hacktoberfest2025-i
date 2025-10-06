import { useState } from "react";
import "./App.css";

type Todo = {
  id: number | null;
  create_at: Date;
  last_update: Date;
  title: string;
  content: string;
};

function App() {
  const todoObjectInitial = {
    id: null,
    create_at: new Date(),
    last_update: new Date(),
    title: "",
    content: "",
  };

  const [todoList, setTodoList] = useState<Todo[]>([
    {
      id: 1,
      create_at: new Date(),
      last_update: new Date(),
      title: "Dummy Data",
      content: "This is dummy data",
    },
  ]);
  const [todo, setTodo] = useState<Todo>(todoObjectInitial);
  const [isEdit, setIsEdit] = useState<boolean>(false);

  const savetodo = () => {
    const todoData = {
      id: todoList.length + 1,
      create_at: new Date(),
      last_update: new Date(),
      title: todo.title,
      content: todo.content,
    };

    setTodoList([...todoList, todoData]);

    setTodo(todoObjectInitial);
  };

  const handleDeleteTodo = (id: number) => {
    setTodoList(todoList.filter((td) => td?.id != id));
  };

  const handleEditTodo = (td: Todo) => {
    setTodo(td);
    setIsEdit(true);
  };

  const updateTodo = () => {
    const updatedTodo = {
      ...todo,
      last_update: new Date(),
    };

    setTodoList((prev) =>
      prev.map((tdItem) =>
        tdItem.id === updatedTodo.id ? updatedTodo : tdItem
      )
    );

    setTodo(todoObjectInitial);
    setIsEdit(false);
  };

  return (
    <div className="space-y-8 w-[50rem]">
      <div className="space-y-8">
        <h1 className="font-bold text-2xl">SIMPLE TODO LIST</h1>
        <section id="FORM" className="space-y-4">
          <div className="flex flex-col space-y-2">
            <label htmlFor="title">TITLE :</label>
            <input
              type="text"
              id="title"
              name="title"
              required
              className="border p-2 rounded-md resize-none"
              value={todo.title}
              onChange={(e) => setTodo({ ...todo, title: e.target.value })}
            />
          </div>
          <div className="flex flex-col space-y-2">
            <label htmlFor="todo-content">CONTENT :</label>
            <textarea
              id="todo-content"
              name="todo-content"
              required
              rows={6}
              className="border p-2 rounded-md resize-none"
              value={todo.content}
              onChange={(e) => setTodo({ ...todo, content: e.target.value })}
            />
          </div>
          <div className="w-full flex justify-end">
            {!isEdit ? (
              <button
                className="p-2 border rounded-xl text-white bg-blue-500 font-semibold cursor-pointer"
                onClick={savetodo}
              >
                Save
              </button>
            ) : (
              <div className="flex gap-x-2">
                <button
                  className="p-2 border rounded-xl text-white bg-blue-500 font-semibold cursor-pointer"
                  onClick={updateTodo}
                >
                  Update
                </button>
                <button
                  className="p-2 border rounded-xl text-white bg-red-500 font-semibold cursor-pointer"
                  onClick={() => {
                    setTodo(todoObjectInitial);
                    setIsEdit(false);
                  }}
                >
                  Cancel
                </button>
              </div>
            )}
          </div>
        </section>
      </div>
      <div className="space-y-4">
        {todoList.map((td) => {
          return (
            <div key={td.id} className="border rounded-md w-full p-4">
              <div className="w-full flex justify-between items-center">
                <p className="font-semibold text-xl">
                  {td.title}
                  {td.create_at.getTime() !== td.last_update.getTime() && (
                    <span className="text-sm text-green-600 ml-2">Updated</span>
                  )}
                </p>
                <div className="flex gap-x-2 items-center">
                  <button
                    className="p-2 border rounded-xl text-white bg-blue-500 font-semibold cursor-pointer"
                    onClick={() => handleEditTodo(td)}
                  >
                    Edit
                  </button>
                  <button
                    className="p-2 border rounded-xl text-white bg-red-500 font-semibold cursor-pointer"
                    onClick={() => td.id !== null && handleDeleteTodo(td.id)}
                  >
                    Delete
                  </button>
                </div>
              </div>
              <p className="whitespace-pre-line">{td.content}</p>
              <p className="text-sm text-gray-500">
                {td.last_update.toLocaleString()}
              </p>
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default App;
