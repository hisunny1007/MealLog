import instance from './axios'

const getProducts = (category) => {
  return instance.get('/pointshop/products', {
    params: { category },
  })
}

const purchaseProduct = (orderRequest) => {
  return instance.post('/pointshop/purchase', orderRequest)
}

const getOrderHistory = () => {
  return instance.get('/pointshop/history')
}

const getPersonalizedRecommendation = () => {
  return instance.get('/pointshop/recommendation')
}

export { getProducts, purchaseProduct, getOrderHistory, getPersonalizedRecommendation }
