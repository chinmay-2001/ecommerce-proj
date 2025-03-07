const prepareFilter = (filterOptions) => {
  const filters = {};
  for (const [key, value] of Object.entries(filterOptions)) {
    filters[key] = value;
  }
  return filters;
};

export { prepareFilter };
