document.addEventListener('DOMContentLoaded', function () {

  /* ========== 1) Lay phan tu ========== */
  const tabs = Array.from(document.querySelectorAll('.tab'));
  const tabContents = Array.from(document.querySelectorAll('.tab-content'));

  const dateInput = document.getElementById('date');
  const timeInput = document.getElementById('time');
  const timeSlotsContainer = document.getElementById('timeSlots');

  const spaceContainer = document.getElementById('spaceOptionsContainer');
  const selectedSpaceDisplay = document.getElementById('selectedSpace');
  const hiddenSpaceInput = document.getElementById('space');

  const guestsInput = document.getElementById('guests');

  /* ========== 2) Tabs ========== */
  function showTab(tabId) {
    tabContents.forEach(function (tc) {
      const active = tc.id === tabId;
      tc.classList.toggle('active', active);
      tc.style.display = active ? 'block' : 'none';
    });

    tabs.forEach(function (tab) {
      const active = tab.dataset.tab === tabId;
      tab.classList.toggle('active', active);
      tab.setAttribute('aria-selected', String(active));
    });
  }

  tabs.forEach(function (tab) {
    tab.addEventListener('click', function () {
      showTab(tab.dataset.tab);
    });
  });

  document.querySelectorAll('.btn-next').forEach(function (btn) {
    btn.addEventListener('click', function () {
      const current = btn.closest('.tab-content');
      const idx = tabContents.indexOf(current);
      if (idx >= 0 && idx + 1 < tabContents.length) {
        showTab(tabContents[idx + 1].id);
      }
    });
  });

  document.querySelectorAll('.btn-back').forEach(function (btn) {
    btn.addEventListener('click', function () {
      const current = btn.closest('.tab-content');
      const idx = tabContents.indexOf(current);
      if (idx > 0) {
        showTab(tabContents[idx - 1].id);
      }
    });
  });

  showTab('tab1');

  /* ========== 3) Ngay mac dinh (hom nay) ========== */
  if (dateInput) {
    const today = new Date();
    const yyyy = today.getFullYear();
    const mm = String(today.getMonth() + 1).padStart(2, '0');
    const dd = String(today.getDate()).padStart(2, '0');
    const todayStr = yyyy + '-' + mm + '-' + dd;

    dateInput.min = todayStr;
    if (!dateInput.value) {
      dateInput.value = todayStr;
    }
  }

  /* ========== 4) Tao Time Slots 10:00 - 22:00 moi 30 phut ========== */
  const TIME_START_HOUR = 10;
  const TIME_END_HOUR = 22;
  const STEP_MINUTES = 90;

  function pad2(n) {
    return String(n).padStart(2, '0');
  }

  function buildTimeSlots() {
    if (!timeSlotsContainer) return [];

    timeSlotsContainer.innerHTML = '';
    const slots = [];

    for (let h = TIME_START_HOUR; h <= TIME_END_HOUR; h++) {
      for (let m = 0; m < 60; m += STEP_MINUTES) {
        if (h === TIME_END_HOUR && m > 0) break;

        const timeStr = pad2(h) + ':' + pad2(m);
        const btn = document.createElement('button');
        btn.type = 'button';
        btn.className = 'time-slot-btn';
        btn.dataset.time = timeStr;
        btn.textContent = timeStr;

        timeSlotsContainer.appendChild(btn);
        slots.push(btn);
      }
    }

    return slots;
  }

  const timeSlotButtons = buildTimeSlots();

  if (timeSlotsContainer && timeSlotButtons.length > 0) {
    timeSlotsContainer.addEventListener('click', function (e) {
      const btn = e.target.closest('.time-slot-btn');
      if (!btn) return;

      timeSlotButtons.forEach(function (b) {
        b.classList.remove('active');
      });

      btn.classList.add('active');
      if (timeInput) {
        timeInput.value = btn.dataset.time;
      }
    });

    if (timeInput && !timeInput.value && timeSlotButtons[0]) {
      timeSlotButtons[0].classList.add('active');
      timeInput.value = timeSlotButtons[0].dataset.time;
    }

    if (timeInput && timeInput.value) {
      const found = timeSlotButtons.find(function (b) {
        return b.dataset.time === timeInput.value;
      });

      if (found) {
        timeSlotButtons.forEach(function (b) {
          b.classList.remove('active');
        });
        found.classList.add('active');
      }
    }
  }

  /* ========== 5) Chon Khong gian ========== */
  function setSelectedSpaceText(text) {
    if (selectedSpaceDisplay) {
      selectedSpaceDisplay.textContent = text || 'Chua chon';
    }
  }

  function setHiddenSpaceValue(value) {
    if (hiddenSpaceInput) {
      hiddenSpaceInput.value = value || '';
    }
  }

  if (spaceContainer) {
    const radios = Array.from(spaceContainer.querySelectorAll('input[type="radio"][name="space"]'));

    if (radios.length > 0) {
      function applyFromRadio(radio) {
        const label = spaceContainer.querySelector('label[for="' + radio.id + '"]');
        const text = label ? label.textContent.trim() : radio.value;
        setSelectedSpaceText(text);
        setHiddenSpaceValue(radio.value);
      }

      spaceContainer.addEventListener('change', function (e) {
        const radio = e.target.closest('input[type="radio"][name="space"]');
        if (radio) applyFromRadio(radio);
      });

      const checked = radios.find(function (r) {
        return r.checked;
      });

      if (checked) {
        applyFromRadio(checked);
      } else {
        const defaultRadio = radios.find(function (r) {
          return r.value === 'lau1';
        }) || radios[0];

        if (defaultRadio) {
          defaultRadio.checked = true;
          applyFromRadio(defaultRadio);
        } else {
          setSelectedSpaceText('Chua chon');
          setHiddenSpaceValue('');
        }
      }
    } else {
      const buttons = Array.from(spaceContainer.querySelectorAll('.space-btn'));

      if (buttons.length > 0) {
        function selectBtn(btn) {
          buttons.forEach(function (b) {
            b.classList.remove('selected');
            b.setAttribute('aria-pressed', 'false');
          });

          btn.classList.add('selected');
          btn.setAttribute('aria-pressed', 'true');

          const value = btn.dataset.value || btn.textContent.trim();
          setSelectedSpaceText(btn.textContent.trim());
          setHiddenSpaceValue(value);
        }

        spaceContainer.addEventListener('click', function (e) {
          const btn = e.target.closest('.space-btn');
          if (btn && !btn.disabled) {
            selectBtn(btn);
          }
        });

        const defaultBtn = buttons.find(function (b) {
          return b.dataset.value === 'lau1';
        }) || buttons[0];

        if (defaultBtn) {
          selectBtn(defaultBtn);
        }
      } else {
        setSelectedSpaceText('Chua chon');
        setHiddenSpaceValue('');
      }
    }
  }

  /* ========== 6) Sanh tiec theo so khach (>= 20) ========== */
  function toggleHallByGuests() {
    if (!spaceContainer) return;

    const count = parseInt((guestsInput ? guestsInput.value : '0'), 10);

    const hallRadio = spaceContainer.querySelector('#space-sanhtiec');
    const hallLabel = spaceContainer.querySelector('label[for="space-sanhtiec"]');

    if (hallRadio || hallLabel) {
      const visible = count >= 20;

      [hallRadio, hallLabel].forEach(function (el) {
        if (!el) return;
        el.style.display = visible ? '' : 'none';
      });

      if (!visible && hallRadio && hallRadio.checked) {
        hallRadio.checked = false;
        setSelectedSpaceText('Chua chon');
        setHiddenSpaceValue('');
      }
    }

    const hallBtn = spaceContainer.querySelector('.space-btn[data-value="sanhtiec"]');

    if (hallBtn) {
      const visible = count >= 20;
      hallBtn.style.display = visible ? '' : 'none';

      if (!visible && hallBtn.classList.contains('selected')) {
        hallBtn.classList.remove('selected');
        hallBtn.setAttribute('aria-pressed', 'false');
        setSelectedSpaceText('Chua chon');
        setHiddenSpaceValue('');
      }
    }
  }

  if (guestsInput) {
    toggleHallByGuests();
    guestsInput.addEventListener('input', toggleHallByGuests);
    guestsInput.addEventListener('change', toggleHallByGuests);
  }

});