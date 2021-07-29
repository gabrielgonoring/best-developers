export const USER_KEY = "@bdev-user";
export const getUser= () => JSON.parse(localStorage.getItem(USER_KEY));
export const setUser = user => {
  localStorage.setItem(USER_KEY, JSON.stringify(user));
};
export const removeUser = () => {
  localStorage.removeItem(USER_KEY);
};