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

function addBackgroundToGenreBadges() {
  const genres = document.getElementById("select-genres-wrapper");
  const elements = genres.querySelectorAll("[data-title]");

  for (const option of elements) {
    option.parentElement.style.backgroundColor = genreColors[option.innerText.trim()];
  }
}

function toggleTVFields() {
  const typeTv = document.getElementById("hs-rtv");

  const tvFields = document.getElementById("tv-fields");

  if (typeTv.checked) {
    tvFields.style.display = "block";
  } else {
    tvFields.style.display = "none";
  }
}

document.addEventListener("DOMContentLoaded", function() {
  setInterval(addBackgroundToGenreBadges, 100);

  const numberInputs = [
    document.getElementById("year-input"),
    document.getElementById("duration-input"),
    document.getElementById("episodes-number-input"),
    document.getElementById("total-episodes-number-input"),
    document.getElementById("seasons-input")
  ];
  setInterval(function() {
    for (const input of numberInputs) {
      if (input.value === '0') {
        input.value = '';
      }
    }
  }, 10);

  document.getElementById("image-input").addEventListener("change", function (event) {
    document.getElementById("image-preview").src = "https://" + event.target.value;
  });

  document.getElementById("synopsis").addEventListener("input", updateCounter);

  document.getElementById("hs-movie").addEventListener("change", toggleTVFields);
  document.getElementById("hs-rtv").addEventListener("change", toggleTVFields);
});