function updateCounter() {
  const textarea = document.getElementById('synopsis');
  const counter = document.getElementById('charCounter');
  const maxLength = 500;
  const currentLength = textarea.value.length;
  counter.textContent = `${currentLength} / ${maxLength}`;
}