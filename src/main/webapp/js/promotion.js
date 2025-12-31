/**
 * 
 */
const buttons = document.querySelectorAll('.filter-btn');
const cards = document.querySelectorAll('.deal-card');
const empty = document.getElementById('empty');

buttons.forEach(btn => {
  btn.addEventListener('click', () => {
    buttons.forEach(b => b.classList.remove('active'));
    btn.classList.add('active');

    const type = btn.dataset.filter;
    let visible = 0;

    cards.forEach(card => {
      if (type === 'all' || card.dataset.type === type) {
        card.style.display = '';
        visible++;
      } else {
        card.style.display = 'none';
      }
    });

    empty.classList.toggle('active', visible === 0);
  });
});

/* Countdown Flash Sale */
function startCountdown() {
  document.querySelectorAll('.countdown').forEach(el => {
    const end = new Date(el.dataset.ends).getTime();

    setInterval(() => {
      const now = Date.now();
      const diff = end - now;

      if (diff <= 0) {
        el.textContent = 'Đã kết thúc';
        return;
      }

      const h = Math.floor(diff / 3600000);
      const m = Math.floor((diff % 3600000) / 60000);
      const s = Math.floor((diff % 60000) / 1000);

      el.textContent = `⏳ ${h}:${m}:${s}`;
    }, 1000);
  });
}

startCountdown();
