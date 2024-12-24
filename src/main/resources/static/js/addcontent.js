const genreColors = {
  "Action": "#ef4444",
  "Adult": "#4b5563",
  "Adventure": "#f59e0b",
  "Animation": "#ec4899",
  "Biography": "#3b82f6",
  "Comedy": "#10b981",
  "Crime": "#dc2626",
  "Documentary": "#14b8a6",
  "Drama": "#6366f1",
  "Family": "#f97316",
  "Fantasy": "#8b5cf6",
  "Noir": "#1f2937",
  "History": "#ca8a04",
  "Horror": "#000000",
  "Musical": "#f43f5e",
  "Mystery": "#4338ca",
  "Reality": "#1d4ed8",
  "Romance": "#db2777",
  "Sci-fi": "#06b6d4",
  "Sport": "#059669",
  "Thriller": "#b91c1c",
  "War": "#111827",
  "Western": "#f59e0b"
};

function updateCounter() {
  const textarea = document.getElementById('synopsis');
  const counter = document.getElementById('charCounter');
  const maxLength = 500;
  const currentLength = textarea.value.length;
  counter.textContent = `${currentLength} / ${maxLength}`;
}

document.addEventListener("DOMContentLoaded", function() {
  setInterval(function() {
    const genres = document.getElementById("select-genres-wrapper");
    const elements = genres.querySelectorAll("[data-title]");

    for (const option of elements) {
      option.parentElement.style.backgroundColor = genreColors[option.innerText.trim()];
    }
  }, 100);
});

function toggleFields() {
  // Ottieni i valori dei radio button
  const typeMovie = document.getElementById("hs-movie");
  const typeTv = document.getElementById("hs-rtv");

  // Ottieni il div che contiene i campi specifici per la TV
  const tvFields = document.getElementById("tv-fields");

  // Se "TV" è selezionato, mostra i campi TV, altrimenti nascondili
  if (typeTv.checked) {
    tvFields.style.display = "block";
  } else {
    tvFields.style.display = "none";
  }
}