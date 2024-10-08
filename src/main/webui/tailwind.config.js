/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    '../resources/**/*.{js,jsx,ts,tsx,html}'
  ],
  theme: {
    extend: {},
  },
  plugins: [
    require('daisyui')
  ],
}

