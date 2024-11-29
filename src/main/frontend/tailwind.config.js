/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
      "../resources/templates/**/*.{html,jte,js}",
      "./node_modules/preline/dist/*.js",
  ],
  theme: {
    extend: {},
  },
  plugins: [
    require('@tailwindcss/forms'),
    require("preline/plugin")
  ],
}